package com.goev.central.service.customer;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerDetailsDto;
import com.goev.central.dto.customer.detail.CustomerDto;

public interface CustomerService {
    CustomerDetailsDto createCustomer(CustomerDetailsDto customerDto);
    CustomerDetailsDto updateCustomer(String customerUUID, CustomerDetailsDto credentials);
    CustomerDetailsDto getCustomerDetails(String customerUUID);
    Boolean deleteCustomer(String customerUUID);
    PaginatedResponseDto<CustomerDto> getCustomers();
}
