package com.goev.central.service.customer.notification.impl;

import com.goev.central.dao.customer.notification.CustomerNotificationDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.notification.CustomerNotificationDto;
import com.goev.central.repository.customer.notification.CustomerNotificationRepository;
import com.goev.central.service.customer.notification.CustomerNotificationService;
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
public class CustomerNotificationServiceImpl implements CustomerNotificationService {

    private final CustomerNotificationRepository customerNotificationRepository;

    @Override
    public PaginatedResponseDto<CustomerNotificationDto> getCustomerNotifications(String customerUUID) {
        PaginatedResponseDto<CustomerNotificationDto> result = PaginatedResponseDto.<CustomerNotificationDto>builder().elements(new ArrayList<>()).build();
        List<CustomerNotificationDao> customerNotificationDaos = customerNotificationRepository.findAllActive();
        if (CollectionUtils.isEmpty(customerNotificationDaos))
            return result;

        for (CustomerNotificationDao customerNotificationDao : customerNotificationDaos) {
            result.getElements().add(CustomerNotificationDto.builder()
                    .uuid(customerNotificationDao.getUuid())
                    .build());
        }
        return result;
    }


    @Override
    public CustomerNotificationDto getCustomerNotificationDetails(String customerUUID, String customerNotificationUUID) {
        CustomerNotificationDao customerNotificationDao = customerNotificationRepository.findByUUID(customerNotificationUUID);
        if (customerNotificationDao == null)
            throw new ResponseException("No customerNotification  found for Id :" + customerNotificationUUID);
        return CustomerNotificationDto.builder()
                .uuid(customerNotificationDao.getUuid()).build();
    }

}
