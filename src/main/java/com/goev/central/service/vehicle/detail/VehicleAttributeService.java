package com.goev.central.service.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleAttributeDto;

public interface VehicleAttributeService {
    PaginatedResponseDto<VehicleAttributeDto> getVehicleAttributes(String vehicleUUID);

    VehicleAttributeDto createVehicleAttribute(String vehicleUUID, VehicleAttributeDto vehicleAttributeDto);

    VehicleAttributeDto updateVehicleAttribute(String vehicleUUID, String vehicleAttributeUUID, VehicleAttributeDto vehicleAttributeDto);

    VehicleAttributeDto getVehicleAttributeDetails(String vehicleUUID, String vehicleAttributeUUID);

    Boolean deleteVehicleAttribute(String vehicleUUID, String vehicleAttributeUUID);
}
