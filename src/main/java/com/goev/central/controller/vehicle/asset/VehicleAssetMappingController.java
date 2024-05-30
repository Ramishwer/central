package com.goev.central.controller.vehicle.asset;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.service.vehicle.asset.VehicleAssetMappingService;
import com.goev.central.service.vehicle.detail.VehicleService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleAssetMappingController {


    private final VehicleAssetMappingService vehicleAssetMappingService;


    @GetMapping("/vehicles/{vehicle-uuid}/assets")
    public ResponseDto<PaginatedResponseDto<AssetDto>> getAssetsForVehicles(@PathVariable(value = "vehicle-uuid") String vehicleUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleAssetMappingService.getAssetsForVehicles(vehicleUUID));
    }

    @PostMapping("/vehicles/{vehicle-uuid}/assets")
    public ResponseDto<AssetDto> createAssetsForVehicle(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @RequestBody AssetDto assetDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleAssetMappingService.createAssetsForVehicle(assetDto, vehicleUUID));
    }

    @PostMapping("/vehicles/{vehicle-uuid}/assets/bulk")
    public ResponseDto<List<AssetDto>> createAssetsForVehicle(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @RequestBody List<AssetDto> assetDtoList) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleAssetMappingService.createBulkAssetsForVehicle(assetDtoList, vehicleUUID));
    }

    @DeleteMapping("/vehicles/{vehicle-uuid}/assets/{asset-uuid}")
    public ResponseDto<Boolean> deleteAssetsForVehicle(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @PathVariable(value = "asset-uuid") String assetUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleAssetMappingService.deleteAssetsForVehicle(assetUUID, vehicleUUID));
    }
}