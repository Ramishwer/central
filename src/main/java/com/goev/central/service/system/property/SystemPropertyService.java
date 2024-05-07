package com.goev.central.service.system.property;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.property.SystemPropertyDto;

public interface SystemPropertyService {
    PaginatedResponseDto<SystemPropertyDto> getSystemProperties();

    SystemPropertyDto createSystemProperty(SystemPropertyDto systemPropertyDto);

    SystemPropertyDto updateSystemProperty(String systemPropertyUUID, SystemPropertyDto systemPropertyDto);

    SystemPropertyDto getSystemPropertyDetails(String systemPropertyUUID);

    Boolean deleteSystemProperty(String systemPropertyUUID);
}
