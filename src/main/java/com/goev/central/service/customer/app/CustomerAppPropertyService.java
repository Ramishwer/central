package com.goev.central.service.customer.app;

import com.goev.central.dto.customer.app.CustomerAppPropertyDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface CustomerAppPropertyService {
    PaginatedResponseDto<CustomerAppPropertyDto> getCustomerAppProperties();

    CustomerAppPropertyDto createCustomerAppProperty(CustomerAppPropertyDto customerAppPropertyDto);

    CustomerAppPropertyDto updateCustomerAppProperty(String customerAppPropertyUUID, CustomerAppPropertyDto customerAppPropertyDto);

    CustomerAppPropertyDto getCustomerAppPropertyDetails(String customerAppPropertyUUID);

    Boolean deleteCustomerAppProperty(String customerAppPropertyUUID);
}
