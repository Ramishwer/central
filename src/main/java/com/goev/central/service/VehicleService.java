package com.goev.central.service;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.VehicleDetailsDto;
import com.goev.central.dto.vehicle.VehicleDto;

import java.util.List;

public interface VehicleService {
    VehicleDetailsDto createVehicle(VehicleDetailsDto vehicleDto);
    VehicleDetailsDto updateVehicle(String vehicleUUID, VehicleDetailsDto credentials);
    VehicleDetailsDto getVehicleDetails(String vehicleUUID);
    Boolean deleteVehicle(String vehicleUUID);

    PaginatedResponseDto<VehicleDto> getVehicles();
}
