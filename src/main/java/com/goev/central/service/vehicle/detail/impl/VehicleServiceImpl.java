package com.goev.central.service.vehicle.detail.impl;


import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dao.vehicle.detail.VehicleDetailDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleActionDto;
import com.goev.central.dto.vehicle.VehicleStatsDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.VehicleDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentDto;
import com.goev.central.enums.vehicle.VehicleOnboardingStatus;
import com.goev.central.enums.vehicle.VehicleStatus;
import com.goev.central.enums.vehicle.VehicleSubStatus;
import com.goev.central.repository.vehicle.detail.VehicleDetailRepository;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.central.service.vehicle.detail.VehicleService;
import com.goev.lib.exceptions.ResponseException;
import com.google.common.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {


    private final VehicleRepository vehicleRepository;
    private final VehicleDetailRepository vehicleDetailRepository;

    @Override
    public Boolean deleteVehicle(String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);

        vehicleRepository.delete(vehicle.getId());
        return true;
    }

    @Override
    public PaginatedResponseDto<VehicleDto> getVehicleStatus(String status, String recommendationForPartnerUUID) {
        PaginatedResponseDto<VehicleDto> result = PaginatedResponseDto.<VehicleDto>builder().elements(new ArrayList<>()).build();
        List<VehicleDao> vehicles = vehicleRepository.findAllByStatus(status);
        return getVehicleDtoPaginatedResponseDto(vehicles, result);
    }

    @Override
    public VehicleDto updateVehicle(String vehicleUUID, VehicleActionDto vehicleActionDto) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);

        switch (vehicleActionDto.getAction()){
            case DEBOARD -> {
                vehicle = updateOnboardingStatus(vehicle,VehicleOnboardingStatus.DEBOARDED);
            }
            case MARK_AVAILABLE, RELEASE_VEHICLE -> { vehicle = releaseVehicle(vehicle,vehicleActionDto);}
            case SEND_TO_MAINTENANCE -> {
                vehicle = sendToMaintenance(vehicle,vehicleActionDto);
            }

        }
        return VehicleDto.fromDao(vehicle);
    }

    private VehicleDao sendToMaintenance(VehicleDao vehicle, VehicleActionDto vehicleActionDto) {
        vehicle.setStatus(VehicleStatus.MAINTENANCE.name());
        vehicle.setSubStatus(VehicleSubStatus.NOT_ASSIGNED.name());
        vehicle.setPartnerDetails(null);
        vehicle.setPartnerId(null);
        vehicle = vehicleRepository.update(vehicle);
        VehicleDetailDao vehicleDetailDao = vehicleDetailRepository.findById(vehicle.getVehicleDetailsId());
        if(vehicleDetailDao!=null) {
            vehicleDetailDao.setRemark(vehicleActionDto.getRemark());
            vehicleDetailDao = vehicleDetailRepository.update(vehicleDetailDao);
            vehicle.setVehicleDetailsId(vehicleDetailDao.getVehicleId());
        }
        VehicleViewDto vehicleViewDto = VehicleViewDto.fromDao(vehicle);
        if(vehicleViewDto!=null) {
            vehicleViewDto.setRemark(vehicleActionDto.getRemark());
            vehicle.setViewInfo(ApplicationConstants.GSON.toJson(vehicleViewDto));
        }
        vehicle =vehicleRepository.update(vehicle);

        return vehicle;
    }

    private VehicleDao releaseVehicle(VehicleDao vehicle, VehicleActionDto vehicleActionDto) {
        vehicle.setStatus(VehicleStatus.AVAILABLE.name());
        vehicle.setSubStatus(VehicleSubStatus.NOT_ASSIGNED.name());
        vehicle.setPartnerDetails(null);
        vehicle.setPartnerId(null);
        vehicle =vehicleRepository.update(vehicle);
        return vehicle;
    }

    private VehicleDao updateOnboardingStatus(VehicleDao vehicle, VehicleOnboardingStatus status) {
        vehicle.setOnboardingStatus(status.name());
        vehicle = vehicleRepository.update(vehicle);
        return vehicle;
    }

    private PaginatedResponseDto<VehicleDto> getVehicleDtoPaginatedResponseDto(List<VehicleDao> vehicles, PaginatedResponseDto<VehicleDto> result) {
        if (CollectionUtils.isEmpty(vehicles))
            return result;
        for (VehicleDao vehicle : vehicles) {
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setUuid(vehicle.getUuid());
            vehicleDto.setPlateNumber(vehicle.getPlateNumber());
            vehicleDto.setStatus(vehicle.getStatus());
            vehicleDto.setPartnerDetails(ApplicationConstants.GSON.fromJson(vehicle.getPartnerDetails(), PartnerViewDto.class));
            vehicleDto.setSubStatus(vehicle.getSubStatus());
            if(vehicle.getStats()!=null) {
                vehicleDto.setStats(ApplicationConstants.GSON.fromJson(vehicle.getStats(), VehicleStatsDto.class ));
            }
            if(vehicle.getSegments()!=null) {
                Type t = new TypeToken<List<VehicleSegmentDto>>(){}.getRawType();
                vehicleDto.setSegments(ApplicationConstants.GSON.fromJson(vehicle.getSegments(),t ));
            }
            vehicleDto.setVehicleDetails(VehicleViewDto.fromDao(vehicle));
            result.getElements().add(vehicleDto);
        }
        return result;
    }

    @Override
    public PaginatedResponseDto<VehicleViewDto> getVehicles() {
        PaginatedResponseDto<VehicleViewDto> result = PaginatedResponseDto.<VehicleViewDto>builder().elements(new ArrayList<>()).build();
        List<VehicleDao> vehicles = vehicleRepository.findAllActive();
        return getVehicleViewDtoPaginatedResponseDto(vehicles, result);
    }

    @Override
    public PaginatedResponseDto<VehicleViewDto> getVehicles(String onboardingStatus) {
        PaginatedResponseDto<VehicleViewDto> result = PaginatedResponseDto.<VehicleViewDto>builder().elements(new ArrayList<>()).build();
        List<VehicleDao> vehicles = vehicleRepository.findAllByOnboardingStatus(onboardingStatus);
        return getVehicleViewDtoPaginatedResponseDto(vehicles, result);
    }


    private PaginatedResponseDto<VehicleViewDto> getVehicleViewDtoPaginatedResponseDto(List<VehicleDao> vehicles, PaginatedResponseDto<VehicleViewDto> result) {
        if (CollectionUtils.isEmpty(vehicles))
            return result;
        for (VehicleDao vehicle : vehicles) {
            VehicleViewDto vehicleViewDto = ApplicationConstants.GSON.fromJson(vehicle.getViewInfo(), VehicleViewDto.class);
            if (vehicleViewDto == null)
                continue;
            vehicleViewDto.setUuid(vehicle.getUuid());
            result.getElements().add(vehicleViewDto);
        }
        return result;
    }
}
