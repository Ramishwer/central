package com.goev.central.service.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.VehicleActionDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.VehicleDto;

public interface VehicleService {
    PaginatedResponseDto<VehicleViewDto> getVehicles();
    PaginatedResponseDto<VehicleViewDto> getVehicles(String onboardingStatus);

    Boolean deleteVehicle(String vehicleUUID);

    PaginatedResponseDto<VehicleDto> getVehicleStatus(String status, String recommendationForPartnerUUID);

    VehicleDto updateVehicle(String vehicleUUID, VehicleActionDto status);
}
