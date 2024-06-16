package com.goev.central.service.system.log;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.log.SystemLogDto;

public interface SystemLogService {
    PaginatedResponseDto<SystemLogDto> getSystemLogs();

    SystemLogDto getSystemLogDetails(String systemLogUUID);

}
