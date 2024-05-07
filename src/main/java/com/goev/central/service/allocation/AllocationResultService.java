package com.goev.central.service.allocation;

import com.goev.central.dto.allocation.AllocationResultDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface AllocationResultService {
    PaginatedResponseDto<AllocationResultDto> getAllocationResults();

    AllocationResultDto getAllocationResultDetails(String allocationResultUUID);

}
