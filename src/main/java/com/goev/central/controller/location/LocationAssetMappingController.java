package com.goev.central.controller.location;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.service.location.LocationAssetMappingService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/location-management")
@AllArgsConstructor
public class LocationAssetMappingController {


    private final LocationAssetMappingService locationAssetMappingService;


    @GetMapping("/locations/{location-uuid}/assets")
    public ResponseDto<PaginatedResponseDto<AssetDto>> getAssetsForLocations(@PathVariable(value = "location-uuid") String locationUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, locationAssetMappingService.getAssetsForLocations(locationUUID));
    }

    @PostMapping("/locations/{location-uuid}/assets")
    public ResponseDto<AssetDto> createAssetsForLocation(@PathVariable(value = "location-uuid") String locationUUID, @RequestBody AssetDto assetDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, locationAssetMappingService.createAssetsForLocation(assetDto, locationUUID));
    }

    @PostMapping("/locations/{location-uuid}/assets/bulk")
    public ResponseDto<List<AssetDto>> createAssetsForLocation(@PathVariable(value = "location-uuid") String locationUUID, @RequestBody List<AssetDto> assetDtoList) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, locationAssetMappingService.createBulkAssetsForLocation(assetDtoList, locationUUID));
    }

    @DeleteMapping("/locations/{location-uuid}/assets/{asset-uuid}")
    public ResponseDto<Boolean> deleteAssetsForLocation(@PathVariable(value = "location-uuid") String locationUUID, @PathVariable(value = "asset-uuid") String assetUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, locationAssetMappingService.deleteAssetsForLocation(assetUUID, locationUUID));
    }
}