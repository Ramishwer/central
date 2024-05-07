package com.goev.central.service.customer.detail.impl;

import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerDetailDto;
import com.goev.central.dto.customer.detail.CustomerDto;
import com.goev.central.repository.customer.detail.CustomerRepository;
import com.goev.central.service.customer.detail.CustomerDetailService;
import com.goev.central.service.customer.detail.CustomerService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerDetailServiceImpl implements CustomerDetailService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDetailDto createCustomer(CustomerDetailDto customerDto) {

        CustomerDao customerDao = getCustomerDao();
        customerDao = customerRepository.save(customerDao);
        if (customerDao == null)
            throw new ResponseException("Error in saving customer customer");
        return getCustomerDetailDto(customerDao);
    }

    private CustomerDao getCustomerDao() {
        CustomerDao customerDao = new CustomerDao();
        return customerDao;
    }

    @Override
    public CustomerDetailDto getCustomerDetails(String customerUUID) {
        CustomerDao customerDao = customerRepository.findByUUID(customerUUID);
        if (customerDao == null)
            throw new ResponseException("No customer  found for Id :" + customerUUID);
        return getCustomerDetailDto(customerDao);
    }

    private CustomerDetailDto getCustomerDetailDto(CustomerDao customerDao) {
        return CustomerDetailDto.builder()
                .uuid(customerDao.getUuid()).build();
    }

}
