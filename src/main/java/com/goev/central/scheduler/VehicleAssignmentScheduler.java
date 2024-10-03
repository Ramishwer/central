package com.goev.central.scheduler;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.dto.partner.duty.PartnerDutyVehicleDetailsDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.enums.partner.PartnerDutyVehicleStatus;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.enums.partner.PartnerSubStatus;
import com.goev.central.enums.vehicle.VehicleStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerDutyRepository;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.google.common.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class VehicleAssignmentScheduler {
    private final VehicleRepository vehicleRepository;
    private final PartnerRepository partnerRepository;
    private final PartnerDutyRepository partnerDutyRepository;

    @Scheduled(fixedRate = 60 * 1000)
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        List<PartnerDao> allPartners = partnerRepository.findAllUnAssignedPartners();

        Type t= new TypeToken<List<PartnerDutyVehicleDetailsDto>>(){}.getRawType();

        for (PartnerDao partnerDao : allPartners) {
            List<VehicleDao> eligibleVehicles = vehicleRepository.findEligibleVehicleForPartnerId(partnerDao.getId());
            if(CollectionUtils.isEmpty(eligibleVehicles)) {
                log.info("Eligible Vehicles For Partner {} []",partnerDao.getId());
                continue;
            }
            log.info("Eligible Vehicles For Partner {} {}",partnerDao.getId(),eligibleVehicles.stream().map(VehicleDao::getPlateNumber).toList());
            for (VehicleDao vehicle : eligibleVehicles) {
                if(!VehicleStatus.AVAILABLE.name().equals(vehicle.getStatus()))
                    continue;
                PartnerDao existingPartner = partnerRepository.findByVehicleId(vehicle.getId());
                if (existingPartner != null)
                    continue;
                if(partnerDao.getHomeLocationId()==null || vehicle.getHomeLocationId() ==null ||!vehicle.getHomeLocationId().equals(partnerDao.getHomeLocationId()))
                    continue;

                if (PartnerStatus.ON_DUTY.name().equals(partnerDao.getStatus()) && PartnerSubStatus.VEHICLE_NOT_ALLOTTED.name().equals(partnerDao.getSubStatus())) {
                    log.info("Assigning Partner {} to Vehicle {}", partnerDao.getPunchId(), vehicle.getPlateNumber());
                    partnerDao.setStatus(PartnerStatus.ON_DUTY.name());
                    partnerDao.setSubStatus(PartnerSubStatus.VEHICLE_ALLOTTED.name());
                    partnerDao.setVehicleId(vehicle.getId());
                    partnerDao.setVehicleDetails(ApplicationConstants.GSON.toJson(VehicleViewDto.fromDao(vehicle)));
                    partnerRepository.update(partnerDao);
                    if(partnerDao.getPartnerDutyId()!=null){
                        PartnerDutyDao partnerDutyDao = partnerDutyRepository.findById(partnerDao.getPartnerDutyId());
                        List<PartnerDutyVehicleDetailsDto> vehicles = new ArrayList<>();

                        if(partnerDutyDao.getVehicles()!= null){
                            vehicles = ApplicationConstants.GSON.fromJson(partnerDutyDao.getVehicles(),t);
                        }
                        vehicles.add(PartnerDutyVehicleDetailsDto.builder().vehicleDetails(VehicleViewDto.fromDao(vehicle)).plateNumber(vehicle.getPlateNumber()).status(PartnerDutyVehicleStatus.ASSIGNED.name()).assignmentTime(DateTime.now()).build());
                        partnerDutyDao.setVehicles(ApplicationConstants.GSON.toJson(vehicles));
                        partnerDutyRepository.update(partnerDutyDao);
                    }
                    vehicle.setStatus(VehicleStatus.ALLOTTED.name());
                    vehicleRepository.update(vehicle);
                }
            }

        }

    }
}
