package com.goev.central.service.customer.detail;


import com.goev.central.dto.customer.detail.CustomerDetailDto;

public interface CustomerDetailService {
    CustomerDetailDto createCustomer(CustomerDetailDto customerDto);

    CustomerDetailDto getCustomerDetails(String customerUUID);
}
