package com.goev.central.controller.asset;

import com.goev.central.dto.asset.AssetTypeDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.service.asset.AssetTypeService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/asset-management")
@AllArgsConstructor
public class AssetTypeController {

    private final AssetTypeService assetTypeService;

    @GetMapping("/asset-types")
    public ResponseDto<PaginatedResponseDto<AssetTypeDto>> getAssetTypes(@RequestParam(value = "count", required = false) Integer count,
                                                                         @RequestParam(value = "start", required = false) Integer start,
                                                                         @RequestParam(value = "from", required = false) Long from,
                                                                         @RequestParam(value = "to", required = false) Long to,
                                                                         @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetTypeService.getAssetTypes());
    }


    @GetMapping("/asset-types/{asset-type-uuid}")
    public ResponseDto<AssetTypeDto> getAssetTypeDetails(@PathVariable(value = "asset-type-uuid") String assetTypeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetTypeService.getAssetTypeDetails(assetTypeUUID));
    }


    @PostMapping("/asset-types")
    public ResponseDto<AssetTypeDto> createAssetType(@RequestBody AssetTypeDto assetDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetTypeService.createAssetType(assetDto));
    }

    @PutMapping("/asset-types/{asset-type-uuid}")
    public ResponseDto<AssetTypeDto> updateAssetType(@PathVariable(value = "asset-type-uuid") String assetTypeUUID, @RequestBody AssetTypeDto assetDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetTypeService.updateAssetType(assetTypeUUID, assetDto));
    }

    @DeleteMapping("/asset-types/{asset-type-uuid}")
    public ResponseDto<Boolean> deleteAssetType(@PathVariable(value = "asset-type-uuid") String assetTypeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, assetTypeService.deleteAssetType(assetTypeUUID));
    }
}
