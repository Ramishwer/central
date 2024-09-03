package com.goev.central.service.customer.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.CustomerViewDto;

public interface CustomerService {
    PaginatedResponseDto<CustomerViewDto> getCustomers(String onboardingStatus);
}
