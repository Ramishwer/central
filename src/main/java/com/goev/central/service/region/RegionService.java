package com.goev.central.service.region;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.region.RegionDto;

public interface RegionService {
    PaginatedResponseDto<RegionDto> getRegions();

    RegionDto createRegion(RegionDto regionDto);

    RegionDto updateRegion(String regionUUID, RegionDto regionDto);

    RegionDto getRegionDetails(String regionUUID);

    Boolean deleteRegion(String regionUUID);
}
