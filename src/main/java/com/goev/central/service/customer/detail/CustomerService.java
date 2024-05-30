package com.goev.central.service.customer.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.customer.detail.CustomerDetailDto;
import com.goev.central.dto.customer.detail.CustomerDto;

public interface CustomerService {
    PaginatedResponseDto<CustomerViewDto> getCustomers();
}
