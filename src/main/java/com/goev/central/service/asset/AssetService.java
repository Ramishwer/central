package com.goev.central.service.asset;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.asset.AssetMappingDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface AssetService {
    PaginatedResponseDto<AssetDto> getAssets();

    AssetDto createAsset(AssetDto assetDto);

    AssetDto updateAsset(String assetUUID, AssetDto assetDto);

    AssetDto getAssetDetails(String assetUUID);

    String getAssetQr(String assetUUID);

    Boolean deleteAsset(String assetUUID);

    AssetMappingDto getAssetMappingDetails(String assetUUID);

    AssetMappingDto createAssetMapping(String assetUUID, AssetMappingDto assetDto);

    Boolean deleteAssetMapping(String assetUUID, String assetMappingUUID);

    AssetMappingDto updateAssetMapping(String assetUUID, String assetMappingUUID, AssetMappingDto assetMappingDto);
}
