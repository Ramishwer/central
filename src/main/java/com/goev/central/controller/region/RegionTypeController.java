package com.goev.central.controller.region;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.region.RegionTypeDto;
import com.goev.central.service.region.RegionTypeService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/region-management")
@AllArgsConstructor
public class RegionTypeController {

    private final RegionTypeService regionTypeService;

    @GetMapping("/region-types")
    public ResponseDto<PaginatedResponseDto<RegionTypeDto>> getRegionTypes(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, regionTypeService.getRegionTypes());
    }
    @PostMapping("/region-types")
    public ResponseDto<RegionTypeDto> createRegionType(@RequestBody RegionTypeDto regionTypeDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, regionTypeService.createRegionType(regionTypeDto));
    }

    @PutMapping("/region-types/{regionType-uuid}")
    public ResponseDto<RegionTypeDto> updateRegionType(@PathVariable(value = "regionType-uuid")String regionTypeUUID, @RequestBody RegionTypeDto regionTypeDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, regionTypeService.updateRegionType(regionTypeUUID,regionTypeDto));
    }

    @GetMapping("/region-types/{regionType-uuid}")
    public ResponseDto<RegionTypeDto> getRegionTypeDetails(@PathVariable(value = "regionType-uuid")String regionTypeUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, regionTypeService.getRegionTypeDetails(regionTypeUUID));
    }

    @DeleteMapping("/region-types/{regionType-uuid}")
    public ResponseDto<Boolean> deleteRegionType(@PathVariable(value = "regionType-uuid")String regionTypeUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, regionTypeService.deleteRegionType(regionTypeUUID));
    }
}
