package com.goev.central.service.vehicle.detail;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.VehicleDetailDto;

import java.util.List;

public interface VehicleService {
    PaginatedResponseDto<VehicleViewDto> getVehicles();
    Boolean deleteVehicle(String vehicleUUID);
}
