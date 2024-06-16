package com.goev.central.service.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleModelDto;

public interface VehicleModelService {
    PaginatedResponseDto<VehicleModelDto> getModels();

    VehicleModelDto createModel(VehicleModelDto vehicleModelDto);

    VehicleModelDto updateModel(String modelUUID, VehicleModelDto vehicleModelDto);

    VehicleModelDto getModelDetails(String modelUUID);

    Boolean deleteModel(String modelUUID);

}
