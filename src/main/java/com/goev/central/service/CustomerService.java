package com.goev.central.service;

import com.goev.central.dto.customer.CustomerDetailsDto;

public interface CustomerService {
    CustomerDetailsDto createCustomer(CustomerDetailsDto customerDto);
    CustomerDetailsDto updateCustomer(String customerUUID, CustomerDetailsDto credentials);
    CustomerDetailsDto getCustomerDetails(String customerUUID);
    Boolean deleteCustomer(String customerUUID);

}
