package com.goev.central.scheduler;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.enums.partner.PartnerSubStatus;
import com.goev.central.enums.vehicle.VehicleStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class VehicleAssignmentScheduler {
    private final VehicleRepository vehicleRepository;
    private final PartnerRepository partnerRepository;

    @Scheduled(fixedRate = 60 * 1000)
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        List<PartnerDao> allPartners = partnerRepository.findAllUnAssignedPartners();

        for (PartnerDao partnerDao : allPartners) {
            List<VehicleDao> eligibleVehicles = vehicleRepository.findEligibleVehicleForPartnerId(partnerDao.getId());
            if(CollectionUtils.isEmpty(eligibleVehicles)) {
                log.info("Eligible Vehicles For Partner {} []",partnerDao.getId());
                continue;
            }
            log.info("Eligible Vehicles For Partner {} {}",partnerDao.getId(),eligibleVehicles.stream().map(VehicleDao::getPlateNumber).collect(Collectors.toList()));
            for (VehicleDao vehicle : eligibleVehicles) {
                PartnerDao existingPartner = partnerRepository.findByVehicleId(vehicle.getId());
                if (existingPartner != null)
                    continue;

                if (PartnerStatus.ON_DUTY.name().equals(partnerDao.getStatus()) && PartnerSubStatus.VEHICLE_NOT_ALLOTTED.name().equals(partnerDao.getSubStatus())) {
                    log.info("Assigning Partner {} to Vehicle {}", partnerDao.getPunchId(), vehicle.getPlateNumber());
                    partnerDao.setStatus(PartnerStatus.ON_DUTY.name());
                    partnerDao.setSubStatus(PartnerSubStatus.VEHICLE_ALLOTTED.name());
                    partnerDao.setVehicleId(vehicle.getId());
                    partnerDao.setVehicleDetails(ApplicationConstants.GSON.toJson(VehicleViewDto.fromDao(vehicle)));
                    partnerRepository.update(partnerDao);
                    vehicle.setStatus(VehicleStatus.ALLOTED.name());
                    vehicleRepository.update(vehicle);
                }
            }

        }

    }
}
