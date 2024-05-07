package com.goev.central.service.customer.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.app.CustomerAppStringDto;

public interface CustomerAppStringService {
    PaginatedResponseDto<CustomerAppStringDto> getCustomerAppStrings();

    CustomerAppStringDto createCustomerAppString(CustomerAppStringDto customerAppStringDto);

    CustomerAppStringDto updateCustomerAppString(String customerAppStringUUID, CustomerAppStringDto customerAppStringDto);

    CustomerAppStringDto getCustomerAppStringDetails(String customerAppStringUUID);

    Boolean deleteCustomerAppString(String customerAppStringUUID);
}
