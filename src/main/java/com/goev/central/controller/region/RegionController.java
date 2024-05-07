package com.goev.central.controller.region;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.region.RegionDto;
import com.goev.central.service.region.RegionService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/region-management")
@AllArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/regions")
    public ResponseDto<PaginatedResponseDto<RegionDto>> getRegions(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, regionService.getRegions());
    }
    @PostMapping("/regions")
    public ResponseDto<RegionDto> createRegion(@RequestBody RegionDto regionDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, regionService.createRegion(regionDto));
    }

    @PutMapping("/regions/{region-uuid}")
    public ResponseDto<RegionDto> updateRegion(@PathVariable(value = "region-uuid")String regionUUID, @RequestBody RegionDto regionDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, regionService.updateRegion(regionUUID,regionDto));
    }

    @GetMapping("/regions/{region-uuid}")
    public ResponseDto<RegionDto> getRegionDetails(@PathVariable(value = "region-uuid")String regionUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, regionService.getRegionDetails(regionUUID));
    }

    @DeleteMapping("/regions/{region-uuid}")
    public ResponseDto<Boolean> deleteRegion(@PathVariable(value = "region-uuid")String regionUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, regionService.deleteRegion(regionUUID));
    }
}
