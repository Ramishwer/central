package com.goev.central.service.impl;


import com.goev.central.dao.vehicle.VehicleDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.VehicleDetailsDto;
import com.goev.central.dto.vehicle.VehicleDto;
import com.goev.central.repository.vehicle.VehicleRepository;
import com.goev.central.service.VehicleService;
import com.goev.lib.exceptions.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl  implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Override
    public VehicleDetailsDto createVehicle(VehicleDetailsDto vehicleDto) {
        VehicleDao vehicle = vehicleRepository.save(new VehicleDao().fromDto(vehicleDto.getDetails()));
        if(vehicle == null)
            throw new ResponseException("Error in saving details");
        return VehicleDetailsDto.builder().details(vehicle.toDto()).uuid(vehicle.getUuid()).build();
    }

    @Override
    public VehicleDetailsDto updateVehicle(String vehicleUUID, VehicleDetailsDto vehicleDto) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if(vehicle == null)
            throw new ResponseException("No vehicle found for Id :"+vehicleUUID);
        vehicle = vehicle.fromDto(vehicleDto.getDetails());
        vehicleRepository.update(vehicle);
        if(vehicle == null)
            throw new ResponseException("Error in saving details");
        return VehicleDetailsDto.builder().details(vehicle.toDto()).uuid(vehicle.getUuid()).build();
    }

    @Override
    public VehicleDetailsDto getVehicleDetails(String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if(vehicle == null)
            throw new ResponseException("No vehicle found for Id :"+vehicleUUID);
        return VehicleDetailsDto.builder().details(vehicle.toDto()).uuid(vehicle.getUuid()).build();
    }

    @Override
    public Boolean deleteVehicle(String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if(vehicle == null)
            throw new ResponseException("No vehicle found for Id :"+vehicleUUID);

        vehicleRepository.delete(vehicle.getId());
        return true;
    }

    @Override
    public PaginatedResponseDto<VehicleDto> getVehicles() {

        PaginatedResponseDto<VehicleDto> result = PaginatedResponseDto.<VehicleDto>builder().elements(new ArrayList<>()).build();
        vehicleRepository.findAll().forEach(x->{
            result.getElements().add(x.toDto());
        });
        return result;
    }
}
