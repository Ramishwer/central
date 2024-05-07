package com.goev.central.service.customer.app.impl;

import com.goev.central.dao.customer.app.CustomerAppEventDao;
import com.goev.central.dto.customer.app.CustomerAppEventDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.customer.app.CustomerAppEventRepository;
import com.goev.central.service.customer.app.CustomerAppEventService;
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
public class CustomerAppEventServiceImpl implements CustomerAppEventService {

    private final CustomerAppEventRepository customerAppEventRepository;

    @Override
    public PaginatedResponseDto<CustomerAppEventDto> getCustomerAppEvents(String customerUUID) {
        PaginatedResponseDto<CustomerAppEventDto> result = PaginatedResponseDto.<CustomerAppEventDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<CustomerAppEventDao> customerAppEventDaos = customerAppEventRepository.findAll();
        if (CollectionUtils.isEmpty(customerAppEventDaos))
            return result;

        for (CustomerAppEventDao customerAppEventDao : customerAppEventDaos) {
            result.getElements().add(CustomerAppEventDto.builder()
                    .uuid(customerAppEventDao.getUuid())
                    .build());
        }
        return result;
    }


    @Override
    public CustomerAppEventDto getCustomerAppEventDetails(String customerUUID,String customerAppEventUUID) {
        CustomerAppEventDao customerAppEventDao = customerAppEventRepository.findByUUID(customerAppEventUUID);
        if (customerAppEventDao == null)
            throw new ResponseException("No customerAppEvent  found for Id :" + customerAppEventUUID);
        return CustomerAppEventDto.builder()
                .uuid(customerAppEventDao.getUuid()).build();
    }

}
