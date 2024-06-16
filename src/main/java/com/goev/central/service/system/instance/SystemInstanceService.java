package com.goev.central.service.system.instance;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.instance.SystemInstanceDto;

public interface SystemInstanceService {
    PaginatedResponseDto<SystemInstanceDto> getSystemInstances();

    SystemInstanceDto getSystemInstanceDetails(String systemInstanceUUID);

}
