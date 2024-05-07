package com.goev.central.service.system.string;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.string.SystemStringDto;

public interface SystemStringService {
    PaginatedResponseDto<SystemStringDto> getSystemStrings();

    SystemStringDto createSystemString(SystemStringDto systemStringDto);

    SystemStringDto updateSystemString(String systemStringUUID, SystemStringDto systemStringDto);

    SystemStringDto getSystemStringDetails(String systemStringUUID);

    Boolean deleteSystemString(String systemStringUUID);
}
