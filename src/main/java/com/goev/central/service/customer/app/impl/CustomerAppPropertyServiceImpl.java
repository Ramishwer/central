package com.goev.central.service.customer.app.impl;

import com.goev.central.dao.customer.app.CustomerAppPropertyDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.app.CustomerAppPropertyDto;
import com.goev.central.repository.customer.app.CustomerAppPropertyRepository;
import com.goev.central.service.customer.app.CustomerAppPropertyService;
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
public class CustomerAppPropertyServiceImpl implements CustomerAppPropertyService {

    private final CustomerAppPropertyRepository customerAppPropertyRepository;

    @Override
    public PaginatedResponseDto<CustomerAppPropertyDto> getCustomerAppProperties() {
        PaginatedResponseDto<CustomerAppPropertyDto> result = PaginatedResponseDto.<CustomerAppPropertyDto>builder().elements(new ArrayList<>()).build();
        List<CustomerAppPropertyDao> customerAppPropertyDaos = customerAppPropertyRepository.findAllActive();
        if (CollectionUtils.isEmpty(customerAppPropertyDaos))
            return result;

        for (CustomerAppPropertyDao customerAppPropertyDao : customerAppPropertyDaos) {
            result.getElements().add(CustomerAppPropertyDto.builder()
                    .uuid(customerAppPropertyDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public CustomerAppPropertyDto createCustomerAppProperty(CustomerAppPropertyDto customerAppPropertyDto) {

        CustomerAppPropertyDao customerAppPropertyDao = new CustomerAppPropertyDao();
        customerAppPropertyDao = customerAppPropertyRepository.save(customerAppPropertyDao);
        if (customerAppPropertyDao == null)
            throw new ResponseException("Error in saving customerAppProperty customerAppProperty");
        return CustomerAppPropertyDto.builder()
                .uuid(customerAppPropertyDao.getUuid()).build();
    }

    @Override
    public CustomerAppPropertyDto updateCustomerAppProperty(String customerAppPropertyUUID, CustomerAppPropertyDto customerAppPropertyDto) {
        CustomerAppPropertyDao customerAppPropertyDao = customerAppPropertyRepository.findByUUID(customerAppPropertyUUID);
        if (customerAppPropertyDao == null)
            throw new ResponseException("No customerAppProperty  found for Id :" + customerAppPropertyUUID);
        CustomerAppPropertyDao newCustomerAppPropertyDao = new CustomerAppPropertyDao();


        newCustomerAppPropertyDao.setId(customerAppPropertyDao.getId());
        newCustomerAppPropertyDao.setUuid(customerAppPropertyDao.getUuid());
        customerAppPropertyDao = customerAppPropertyRepository.update(newCustomerAppPropertyDao);
        if (customerAppPropertyDao == null)
            throw new ResponseException("Error in updating details customerAppProperty ");
        return CustomerAppPropertyDto.builder()
                .uuid(customerAppPropertyDao.getUuid()).build();
    }

    @Override
    public CustomerAppPropertyDto getCustomerAppPropertyDetails(String customerAppPropertyUUID) {
        CustomerAppPropertyDao customerAppPropertyDao = customerAppPropertyRepository.findByUUID(customerAppPropertyUUID);
        if (customerAppPropertyDao == null)
            throw new ResponseException("No customerAppProperty  found for Id :" + customerAppPropertyUUID);
        return CustomerAppPropertyDto.builder()
                .uuid(customerAppPropertyDao.getUuid()).build();
    }

    @Override
    public Boolean deleteCustomerAppProperty(String customerAppPropertyUUID) {
        CustomerAppPropertyDao customerAppPropertyDao = customerAppPropertyRepository.findByUUID(customerAppPropertyUUID);
        if (customerAppPropertyDao == null)
            throw new ResponseException("No customerAppProperty  found for Id :" + customerAppPropertyUUID);
        customerAppPropertyRepository.delete(customerAppPropertyDao.getId());
        return true;
    }
}
