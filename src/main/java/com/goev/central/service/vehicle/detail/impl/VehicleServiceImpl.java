package com.goev.central.service.vehicle.detail.impl;


import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.VehicleDto;
import com.goev.central.enums.vehicle.VehicleOnboardingStatus;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.central.service.vehicle.detail.VehicleService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {


    private final VehicleRepository vehicleRepository;

    @Override
    public Boolean deleteVehicle(String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);

        vehicleRepository.delete(vehicle.getId());
        return true;
    }

    @Override
    public PaginatedResponseDto<VehicleDto> getVehicleStatus(String status) {
        PaginatedResponseDto<VehicleDto> result = PaginatedResponseDto.<VehicleDto>builder().elements(new ArrayList<>()).build();
        List<VehicleDao> vehicles = vehicleRepository.findAllByStatus(status);
        return getVehicleDtoPaginatedResponseDto(vehicles, result);
    }

    @Override
    public Boolean updateVehicleOnboardingStatus(String vehicleUUID, VehicleOnboardingStatus status) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);

        vehicle.setOnboardingStatus(status.name());
        vehicleRepository.update(vehicle);
        return true;
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
