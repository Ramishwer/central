package com.goev.central.service;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.CustomerDetailsDto;
import com.goev.central.dto.customer.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDetailsDto createCustomer(CustomerDetailsDto customerDto);
    CustomerDetailsDto updateCustomer(String customerUUID, CustomerDetailsDto credentials);
    CustomerDetailsDto getCustomerDetails(String customerUUID);
    Boolean deleteCustomer(String customerUUID);
    PaginatedResponseDto<CustomerDto> getCustomers();
}
