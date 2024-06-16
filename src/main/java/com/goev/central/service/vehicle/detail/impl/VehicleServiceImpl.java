package com.goev.central.service.vehicle.detail.impl;


import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
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
    public PaginatedResponseDto<VehicleViewDto> getVehicles() {
        PaginatedResponseDto<VehicleViewDto> result = PaginatedResponseDto.<VehicleViewDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<VehicleDao> vehicles = vehicleRepository.findAllActive();
        return getVehicleViewDtoPaginatedResponseDto(vehicles, result);
    }

    @Override
    public PaginatedResponseDto<VehicleViewDto> getVehicles(String onboardingStatus) {
        PaginatedResponseDto<VehicleViewDto> result = PaginatedResponseDto.<VehicleViewDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
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
