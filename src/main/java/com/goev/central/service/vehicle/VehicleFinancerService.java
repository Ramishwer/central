package com.goev.central.service.vehicle;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleFinancerDto;

public interface VehicleFinancerService {
    PaginatedResponseDto<VehicleFinancerDto> getFinancers();
    VehicleFinancerDto createFinancer(VehicleFinancerDto vehicleFinancerDto);
    VehicleFinancerDto updateFinancer(String financerUUID, VehicleFinancerDto vehicleFinancerDto);
    VehicleFinancerDto getFinancerDetails(String financerUUID);
    Boolean deleteFinancer(String financerUUID);
    
}
