package com.goev.central.service.customer.notification;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.notification.CustomerNotificationDto;

public interface CustomerNotificationService {
    PaginatedResponseDto<CustomerNotificationDto> getCustomerNotifications(String customerUUID);

    CustomerNotificationDto getCustomerNotificationDetails(String customerUUID, String customerNotificationUUID);

}
