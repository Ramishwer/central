package com.goev.central.service.partner.asset;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.common.PaginatedResponseDto;

import java.util.List;

public interface PartnerAssetMappingService {
    PaginatedResponseDto<AssetDto> getAssetsForPartners(String partnerUUID);

    AssetDto createAssetsForPartner(AssetDto assetDto, String partnerUUID);

    List<AssetDto> createBulkAssetsForPartner(List<AssetDto> assetDtoList, String partnerUUID);

    Boolean deleteAssetsForPartner(String assetUUID, String partnerUUID);
}
