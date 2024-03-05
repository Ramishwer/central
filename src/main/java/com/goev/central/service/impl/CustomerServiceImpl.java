package com.goev.central.service.impl;


import com.goev.central.dto.customer.CustomerDetailsDto;
import com.goev.central.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerServiceImpl  implements CustomerService {
    @Override
    public CustomerDetailsDto createCustomer(CustomerDetailsDto customerDto) {
        return null;
    }

    @Override
    public CustomerDetailsDto updateCustomer(String customerUUID, CustomerDetailsDto credentials) {
        return null;
    }

    @Override
    public CustomerDetailsDto getCustomerDetails(String customerUUID) {
        return null;
    }

    @Override
    public Boolean deleteCustomer(String customerUUID) {
        return null;
    }
}
