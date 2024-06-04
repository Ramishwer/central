package com.goev.central.controller.asset;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.asset.AssetMappingDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.service.asset.AssetService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/asset-management")
@AllArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @GetMapping("/assets")
    public ResponseDto<PaginatedResponseDto<AssetDto>> getAssets(@RequestParam(value = "count",required = false) Integer count,
                                                                 @RequestParam(value = "start", required = false) Integer start,
                                                                 @RequestParam(value = "from", required = false) Long from,
                                                                 @RequestParam(value = "to", required = false) Long to,
                                                                 @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetService.getAssets());
    }


    @GetMapping("/assets/{asset-uuid}")
    public ResponseDto<AssetDto> getAssetDetails(@PathVariable(value = "asset-uuid") String assetUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetService.getAssetDetails(assetUUID));
    }

    @GetMapping("/assets/{asset-uuid}/qr")
    public ResponseDto<String> getAssetQr(@PathVariable(value = "asset-uuid") String assetUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetService.getAssetQr(assetUUID));
    }


    @PostMapping("/assets")
    public ResponseDto<AssetDto> createAsset(@RequestBody AssetDto assetDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetService.createAsset(assetDto));
    }

    @PutMapping("/assets/{asset-uuid}")
    public ResponseDto<AssetDto> updateAsset(@PathVariable(value = "asset-uuid") String assetUUID, @RequestBody AssetDto assetDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetService.updateAsset(assetUUID, assetDto));
    }

    @DeleteMapping("/assets/{asset-uuid}")
    public ResponseDto<Boolean> deleteAsset(@PathVariable(value = "asset-uuid") String assetUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetService.deleteAsset(assetUUID));
    }

    @GetMapping("/assets/{asset-uuid}/mappings")
    public ResponseDto<AssetMappingDto> getAssetMappingDetails(@PathVariable(value = "asset-uuid") String assetUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetService.getAssetMappingDetails(assetUUID));
    }

    @PostMapping("/assets/{asset-uuid}/mappings")
    public ResponseDto<AssetMappingDto> createAssetMapping(@PathVariable(value = "asset-uuid") String assetUUID,@RequestBody AssetMappingDto assetMappingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetService.createAssetMapping(assetUUID,assetMappingDto));
    }

    @PutMapping("/assets/{asset-uuid}/mappings/{asset-mapping-uuid}")
    public ResponseDto<AssetMappingDto> updateAssetMapping(@PathVariable(value = "asset-uuid") String assetUUID,@PathVariable(value = "asset-mapping-uuid") String assetMappingUUID,@RequestBody AssetMappingDto assetMappingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetService.updateAssetMapping(assetUUID,assetMappingUUID,assetMappingDto));
    }

    @DeleteMapping("/assets/{asset-uuid}/mappings/{asset-mapping-uuid}")
    public ResponseDto<Boolean> deleteAssetMapping(@PathVariable(value = "asset-uuid") String assetUUID,@PathVariable(value = "asset-mapping-uuid") String assetMappingUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetService.deleteAssetMapping(assetUUID,assetMappingUUID));
    }
}
