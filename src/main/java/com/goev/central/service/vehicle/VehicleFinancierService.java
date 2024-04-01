package com.goev.central.service.vehicle;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleFinancierDto;

public interface VehicleFinancierService {
    PaginatedResponseDto<VehicleFinancierDto> getFinanciers();
    VehicleFinancierDto createFinancier(VehicleFinancierDto vehicleFinancierDto);
    VehicleFinancierDto updateFinancier(String financierUUID, VehicleFinancierDto vehicleFinancierDto);
    VehicleFinancierDto getFinancierDetails(String financierUUID);
    Boolean deleteFinancier(String financierUUID);
    
}
