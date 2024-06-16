package com.goev.central.service.customer.segment.impl;

import com.goev.central.dao.customer.segment.CustomerSegmentDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.segment.CustomerSegmentDto;
import com.goev.central.repository.customer.segment.CustomerSegmentRepository;
import com.goev.central.service.customer.segment.CustomerSegmentService;
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
public class CustomerSegmentServiceImpl implements CustomerSegmentService {

    private final CustomerSegmentRepository customerSegmentRepository;

    @Override
    public PaginatedResponseDto<CustomerSegmentDto> getCustomerSegments() {
        PaginatedResponseDto<CustomerSegmentDto> result = PaginatedResponseDto.<CustomerSegmentDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<CustomerSegmentDao> customerSegmentDaos = customerSegmentRepository.findAllActive();
        if (CollectionUtils.isEmpty(customerSegmentDaos))
            return result;

        for (CustomerSegmentDao customerSegmentDao : customerSegmentDaos) {
            result.getElements().add(CustomerSegmentDto.builder()
                    .uuid(customerSegmentDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public CustomerSegmentDto createCustomerSegment(CustomerSegmentDto customerSegmentDto) {

        CustomerSegmentDao customerSegmentDao = new CustomerSegmentDao();
        customerSegmentDao = customerSegmentRepository.save(customerSegmentDao);
        if (customerSegmentDao == null)
            throw new ResponseException("Error in saving customerSegment customerSegment");
        return CustomerSegmentDto.builder()
                .uuid(customerSegmentDao.getUuid()).build();
    }

    @Override
    public CustomerSegmentDto updateCustomerSegment(String customerSegmentUUID, CustomerSegmentDto customerSegmentDto) {
        CustomerSegmentDao customerSegmentDao = customerSegmentRepository.findByUUID(customerSegmentUUID);
        if (customerSegmentDao == null)
            throw new ResponseException("No customerSegment  found for Id :" + customerSegmentUUID);
        CustomerSegmentDao newCustomerSegmentDao = new CustomerSegmentDao();


        newCustomerSegmentDao.setId(customerSegmentDao.getId());
        newCustomerSegmentDao.setUuid(customerSegmentDao.getUuid());
        customerSegmentDao = customerSegmentRepository.update(newCustomerSegmentDao);
        if (customerSegmentDao == null)
            throw new ResponseException("Error in updating details customerSegment ");
        return CustomerSegmentDto.builder()
                .uuid(customerSegmentDao.getUuid()).build();
    }

    @Override
    public CustomerSegmentDto getCustomerSegmentDetails(String customerSegmentUUID) {
        CustomerSegmentDao customerSegmentDao = customerSegmentRepository.findByUUID(customerSegmentUUID);
        if (customerSegmentDao == null)
            throw new ResponseException("No customerSegment  found for Id :" + customerSegmentUUID);
        return CustomerSegmentDto.builder()
                .uuid(customerSegmentDao.getUuid()).build();
    }

    @Override
    public Boolean deleteCustomerSegment(String customerSegmentUUID) {
        CustomerSegmentDao customerSegmentDao = customerSegmentRepository.findByUUID(customerSegmentUUID);
        if (customerSegmentDao == null)
            throw new ResponseException("No customerSegment  found for Id :" + customerSegmentUUID);
        customerSegmentRepository.delete(customerSegmentDao.getId());
        return true;
    }
}
