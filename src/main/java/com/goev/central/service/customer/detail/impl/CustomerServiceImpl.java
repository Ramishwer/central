package com.goev.central.service.customer.detail.impl;

import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerDto;
import com.goev.central.repository.customer.detail.CustomerRepository;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public PaginatedResponseDto<CustomerDto> getCustomers() {
        PaginatedResponseDto<CustomerDto> result = PaginatedResponseDto.<CustomerDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<CustomerDao> customerDaos = customerRepository.findAll();
        if (CollectionUtils.isEmpty(customerDaos))
            return result;

        for (CustomerDao customerDao : customerDaos) {
            result.getElements().add(getCustomerDto(customerDao));
        }
        return result;
    }

    private CustomerDto getCustomerDto(CustomerDao customerDao) {
        return CustomerDto.builder()
                .uuid(customerDao.getUuid())
                .build();
    }

}
