package com.goev.central.service.vehicle;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleManufacturerDto;

public interface VehicleManufacturerService {
    PaginatedResponseDto<VehicleManufacturerDto> getManufacturers();
    VehicleManufacturerDto createManufacturer(VehicleManufacturerDto vehicleManufacturerDto);
    VehicleManufacturerDto updateManufacturer(String manufacturerUUID, VehicleManufacturerDto vehicleManufacturerDto);
    VehicleManufacturerDto getManufacturerDetails(String manufacturerUUID);
    Boolean deleteManufacturer(String manufacturerUUID);

}
