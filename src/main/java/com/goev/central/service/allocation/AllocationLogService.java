package com.goev.central.service.allocation;

import com.goev.central.dto.allocation.AllocationLogDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface AllocationLogService {
    PaginatedResponseDto<AllocationLogDto> getAllocationLogs();
    AllocationLogDto getAllocationLogDetails(String allocationLogUUID);

}
