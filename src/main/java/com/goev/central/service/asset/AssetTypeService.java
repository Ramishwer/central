package com.goev.central.service.asset;

import com.goev.central.dto.asset.AssetTypeDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface AssetTypeService {
    PaginatedResponseDto<AssetTypeDto> getAssetTypes();

    AssetTypeDto createAssetType(AssetTypeDto assetDto);

    AssetTypeDto updateAssetType(String assetUUID, AssetTypeDto assetDto);

    AssetTypeDto getAssetTypeDetails(String assetUUID);

    Boolean deleteAssetType(String assetUUID);
}
