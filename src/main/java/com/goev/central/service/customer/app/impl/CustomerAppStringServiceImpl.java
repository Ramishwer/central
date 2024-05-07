package com.goev.central.service.customer.app.impl;

import com.goev.central.dao.customer.app.CustomerAppStringDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.app.CustomerAppStringDto;
import com.goev.central.repository.customer.app.CustomerAppStringRepository;
import com.goev.central.service.customer.app.CustomerAppStringService;
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
public class CustomerAppStringServiceImpl implements CustomerAppStringService {

    private final CustomerAppStringRepository customerAppStringRepository;

    @Override
    public PaginatedResponseDto<CustomerAppStringDto> getCustomerAppStrings() {
        PaginatedResponseDto<CustomerAppStringDto> result = PaginatedResponseDto.<CustomerAppStringDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<CustomerAppStringDao> customerAppStringDaos = customerAppStringRepository.findAll();
        if (CollectionUtils.isEmpty(customerAppStringDaos))
            return result;

        for (CustomerAppStringDao customerAppStringDao : customerAppStringDaos) {
            result.getElements().add(CustomerAppStringDto.builder()
                    .uuid(customerAppStringDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public CustomerAppStringDto createCustomerAppString(CustomerAppStringDto customerAppStringDto) {

        CustomerAppStringDao customerAppStringDao = new CustomerAppStringDao();
        customerAppStringDao = customerAppStringRepository.save(customerAppStringDao);
        if (customerAppStringDao == null)
            throw new ResponseException("Error in saving customerAppString customerAppString");
        return CustomerAppStringDto.builder()
                .uuid(customerAppStringDao.getUuid()).build();
    }

    @Override
    public CustomerAppStringDto updateCustomerAppString(String customerAppStringUUID, CustomerAppStringDto customerAppStringDto) {
        CustomerAppStringDao customerAppStringDao = customerAppStringRepository.findByUUID(customerAppStringUUID);
        if (customerAppStringDao == null)
            throw new ResponseException("No customerAppString  found for Id :" + customerAppStringUUID);
        CustomerAppStringDao newCustomerAppStringDao = new CustomerAppStringDao();
       

        newCustomerAppStringDao.setId(customerAppStringDao.getId());
        newCustomerAppStringDao.setUuid(customerAppStringDao.getUuid());
        customerAppStringDao = customerAppStringRepository.update(newCustomerAppStringDao);
        if (customerAppStringDao == null)
            throw new ResponseException("Error in updating details customerAppString ");
        return CustomerAppStringDto.builder()
                .uuid(customerAppStringDao.getUuid()).build();
    }

    @Override
    public CustomerAppStringDto getCustomerAppStringDetails(String customerAppStringUUID) {
        CustomerAppStringDao customerAppStringDao = customerAppStringRepository.findByUUID(customerAppStringUUID);
        if (customerAppStringDao == null)
            throw new ResponseException("No customerAppString  found for Id :" + customerAppStringUUID);
        return CustomerAppStringDto.builder()
                .uuid(customerAppStringDao.getUuid()).build();
    }

    @Override
    public Boolean deleteCustomerAppString(String customerAppStringUUID) {
        CustomerAppStringDao customerAppStringDao = customerAppStringRepository.findByUUID(customerAppStringUUID);
        if (customerAppStringDao == null)
            throw new ResponseException("No customerAppString  found for Id :" + customerAppStringUUID);
        customerAppStringRepository.delete(customerAppStringDao.getId());
        return true;
    }
}
