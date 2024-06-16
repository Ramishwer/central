package com.goev.central.controller.partner.asset;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.service.partner.asset.PartnerAssetMappingService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerAssetMappingController {


    private final PartnerAssetMappingService partnerAssetMappingService;


    @GetMapping("/partners/{partner-uuid}/assets")
    public ResponseDto<PaginatedResponseDto<AssetDto>> getAssetsForPartners(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAssetMappingService.getAssetsForPartners(partnerUUID));
    }

    @PostMapping("/partners/{partner-uuid}/assets")
    public ResponseDto<AssetDto> createAssetsForPartner(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody AssetDto assetDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAssetMappingService.createAssetsForPartner(assetDto, partnerUUID));
    }

    @PostMapping("/partners/{partner-uuid}/assets/bulk")
    public ResponseDto<List<AssetDto>> createAssetsForPartner(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody List<AssetDto> assetDtoList) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAssetMappingService.createBulkAssetsForPartner(assetDtoList, partnerUUID));
    }

    @DeleteMapping("/partners/{partner-uuid}/assets/{asset-uuid}")
    public ResponseDto<Boolean> deleteAssetsForPartner(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "asset-uuid") String assetUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAssetMappingService.deleteAssetsForPartner(assetUUID, partnerUUID));
    }
}