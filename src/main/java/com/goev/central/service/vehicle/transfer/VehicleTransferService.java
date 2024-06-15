package com.goev.central.service.vehicle.transfer;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.transfer.VehicleTransferDto;

public interface VehicleTransferService {
    PaginatedResponseDto<VehicleTransferDto> getTransfersForVehicle(String vehicleUUID);
    PaginatedResponseDto<VehicleTransferDto> getTransfers();
}
