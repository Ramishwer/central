package com.goev.central.service.customer.detail.impl;

import com.goev.central.dao.customer.detail.CustomerSessionDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerSessionDto;
import com.goev.central.repository.customer.detail.CustomerSessionRepository;
import com.goev.central.service.customer.detail.CustomerSessionService;
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
public class CustomerSessionServiceImpl implements CustomerSessionService {

    private final CustomerSessionRepository customerSessionRepository;

    @Override
    public PaginatedResponseDto<CustomerSessionDto> getCustomerSessions(String customerUUID) {
        PaginatedResponseDto<CustomerSessionDto> result = PaginatedResponseDto.<CustomerSessionDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<CustomerSessionDao> customerSessionDaos = customerSessionRepository.findAllActive();
        if (CollectionUtils.isEmpty(customerSessionDaos))
            return result;

        for (CustomerSessionDao customerSessionDao : customerSessionDaos) {
            result.getElements().add(CustomerSessionDto.builder()
                    .uuid(customerSessionDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public CustomerSessionDto getCustomerSessionDetails(String customerUUID, String customerSessionUUID) {
        CustomerSessionDao customerSessionDao = customerSessionRepository.findByUUID(customerSessionUUID);
        if (customerSessionDao == null)
            throw new ResponseException("No customerSession  found for Id :" + customerSessionUUID);
        return CustomerSessionDto.builder()
                .uuid(customerSessionDao.getUuid()).build();
    }

    @Override
    public Boolean deleteCustomerSession(String customerUUID, String customerSessionUUID) {
        CustomerSessionDao customerSessionDao = customerSessionRepository.findByUUID(customerSessionUUID);
        if (customerSessionDao == null)
            throw new ResponseException("No customerSession  found for Id :" + customerSessionUUID);
        customerSessionRepository.delete(customerSessionDao.getId());
        return true;
    }
}
