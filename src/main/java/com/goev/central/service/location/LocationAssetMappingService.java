package com.goev.central.service.location;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.common.PaginatedResponseDto;

import java.util.List;

public interface LocationAssetMappingService {
    PaginatedResponseDto<AssetDto> getAssetsForLocations(String locationUUID);

    AssetDto createAssetsForLocation(AssetDto assetDto, String locationUUID);

    List<AssetDto> createBulkAssetsForLocation(List<AssetDto> assetDtoList, String locationUUID);

    Boolean deleteAssetsForLocation(String assetUUID, String locationUUID);
}
