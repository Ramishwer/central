package com.goev.central.service.vehicle;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.VehicleDetailsDto;
import com.goev.central.dto.vehicle.detail.VehicleDto;

public interface VehicleService {
    VehicleDetailsDto createVehicle(VehicleDetailsDto vehicleDto);
    VehicleDetailsDto updateVehicle(String vehicleUUID, VehicleDetailsDto credentials);
    VehicleDetailsDto getVehicleDetails(String vehicleUUID);
    Boolean deleteVehicle(String vehicleUUID);
    PaginatedResponseDto<VehicleViewDto> getVehicles();
}
