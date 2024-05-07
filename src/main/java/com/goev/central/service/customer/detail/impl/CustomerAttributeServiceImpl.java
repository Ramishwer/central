package com.goev.central.service.customer.detail.impl;

import com.goev.central.dao.customer.detail.CustomerAttributeDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerAttributeDto;
import com.goev.central.repository.customer.detail.CustomerAttributeRepository;
import com.goev.central.service.customer.detail.CustomerAttributeService;
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
public class CustomerAttributeServiceImpl implements CustomerAttributeService {

    private final CustomerAttributeRepository customerAttributeRepository;

    @Override
    public PaginatedResponseDto<CustomerAttributeDto> getCustomerAttributes(String customerUUID) {
        PaginatedResponseDto<CustomerAttributeDto> result = PaginatedResponseDto.<CustomerAttributeDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<CustomerAttributeDao> customerAttributeDaos = customerAttributeRepository.findAll();
        if (CollectionUtils.isEmpty(customerAttributeDaos))
            return result;

        for (CustomerAttributeDao customerAttributeDao : customerAttributeDaos) {
            result.getElements().add(CustomerAttributeDto.builder()
                    .uuid(customerAttributeDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public CustomerAttributeDto createCustomerAttribute(String customerUUID, CustomerAttributeDto customerAttributeDto) {

        CustomerAttributeDao customerAttributeDao = new CustomerAttributeDao();
        customerAttributeDao = customerAttributeRepository.save(customerAttributeDao);
        if (customerAttributeDao == null)
            throw new ResponseException("Error in saving customerAttribute customerAttribute");
        return CustomerAttributeDto.builder()
                .uuid(customerAttributeDao.getUuid()).build();
    }

    @Override
    public CustomerAttributeDto updateCustomerAttribute(String customerUUID, String customerAttributeUUID, CustomerAttributeDto customerAttributeDto) {
        CustomerAttributeDao customerAttributeDao = customerAttributeRepository.findByUUID(customerAttributeUUID);
        if (customerAttributeDao == null)
            throw new ResponseException("No customerAttribute  found for Id :" + customerAttributeUUID);
        CustomerAttributeDao newCustomerAttributeDao = new CustomerAttributeDao();
       

        newCustomerAttributeDao.setId(customerAttributeDao.getId());
        newCustomerAttributeDao.setUuid(customerAttributeDao.getUuid());
        customerAttributeDao = customerAttributeRepository.update(newCustomerAttributeDao);
        if (customerAttributeDao == null)
            throw new ResponseException("Error in updating details customerAttribute ");
        return CustomerAttributeDto.builder()
                .uuid(customerAttributeDao.getUuid()).build();
    }

    @Override
    public CustomerAttributeDto getCustomerAttributeDetails(String customerUUID, String customerAttributeUUID) {
        CustomerAttributeDao customerAttributeDao = customerAttributeRepository.findByUUID(customerAttributeUUID);
        if (customerAttributeDao == null)
            throw new ResponseException("No customerAttribute  found for Id :" + customerAttributeUUID);
        return CustomerAttributeDto.builder()
                .uuid(customerAttributeDao.getUuid()).build();
    }

    @Override
    public Boolean deleteCustomerAttribute(String customerUUID, String customerAttributeUUID) {
        CustomerAttributeDao customerAttributeDao = customerAttributeRepository.findByUUID(customerAttributeUUID);
        if (customerAttributeDao == null)
            throw new ResponseException("No customerAttribute  found for Id :" + customerAttributeUUID);
        customerAttributeRepository.delete(customerAttributeDao.getId());
        return true;
    }
}
