package com.goev.central.service.customer.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerAttributeDto;

public interface CustomerAttributeService {
    PaginatedResponseDto<CustomerAttributeDto> getCustomerAttributes(String customerUUID);

    CustomerAttributeDto createCustomerAttribute(String customerUUID, CustomerAttributeDto customerAttributeDto);

    CustomerAttributeDto updateCustomerAttribute(String customerUUID, String customerAttributeUUID, CustomerAttributeDto customerAttributeDto);

    CustomerAttributeDto getCustomerAttributeDetails(String customerUUID, String customerAttributeUUID);

    Boolean deleteCustomerAttribute(String customerUUID, String customerAttributeUUID);
}
