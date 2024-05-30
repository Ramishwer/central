package com.goev.central.service.vehicle.asset;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.VehicleViewDto;

import java.util.List;

public interface VehicleAssetMappingService {
    PaginatedResponseDto<AssetDto> getAssetsForVehicles(String vehicleUUID);

    AssetDto createAssetsForVehicle(AssetDto assetDto, String vehicleUUID);

    List<AssetDto> createBulkAssetsForVehicle(List<AssetDto> assetDtoList, String vehicleUUID);

    Boolean deleteAssetsForVehicle(String assetUUID, String vehicleUUID);
}
