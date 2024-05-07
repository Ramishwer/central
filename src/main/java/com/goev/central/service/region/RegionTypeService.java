package com.goev.central.service.region;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.region.RegionTypeDto;

public interface RegionTypeService {
    PaginatedResponseDto<RegionTypeDto> getRegionTypes();

    RegionTypeDto createRegionType(RegionTypeDto regionTypeDto);

    RegionTypeDto updateRegionType(String regionTypeUUID, RegionTypeDto regionTypeDto);

    RegionTypeDto getRegionTypeDetails(String regionTypeUUID);

    Boolean deleteRegionType(String regionTypeUUID);
}
