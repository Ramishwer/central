package com.goev.central.service.impl;

import com.goev.central.dto.vehicle.VehicleDetailsDto;
import com.goev.central.dto.vehicle.VehicleDto;
import com.goev.central.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    @Override
    public VehicleDetailsDto createVehicle(VehicleDetailsDto vehicleDto) {
        return null;
    }

    @Override
    public VehicleDetailsDto updateVehicle(String vehicleUUID, VehicleDetailsDto credentials) {
        return null;
    }

    @Override
    public VehicleDetailsDto getVehicleDetails(String vehicleUUID) {
        return null;
    }

    @Override
    public Boolean deleteVehicle(String vehicleUUID) {
        return null;
    }

    @Override
    public List<VehicleDto> getVehicles() {
        return new ArrayList<>();
    }
}
