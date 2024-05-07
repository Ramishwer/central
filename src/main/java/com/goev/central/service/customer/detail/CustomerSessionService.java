package com.goev.central.service.customer.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerSessionDto;

public interface CustomerSessionService {
    PaginatedResponseDto<CustomerSessionDto> getCustomerSessions(String customerUUID);
    
    CustomerSessionDto getCustomerSessionDetails(String customerUUID, String customerSessionUUID);

    Boolean deleteCustomerSession(String customerUUID, String customerSessionUUID);
}
