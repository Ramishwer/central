package com.goev.central.service.customer.notification;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.notification.CustomerNotificationTemplateDto;

public interface CustomerNotificationTemplateService {
    PaginatedResponseDto<CustomerNotificationTemplateDto> getCustomerNotificationTemplates();

    CustomerNotificationTemplateDto createCustomerNotificationTemplate(CustomerNotificationTemplateDto customerNotificationTemplateDto);

    CustomerNotificationTemplateDto updateCustomerNotificationTemplate(String customerNotificationTemplateUUID, CustomerNotificationTemplateDto customerNotificationTemplateDto);

    CustomerNotificationTemplateDto getCustomerNotificationTemplateDetails(String customerNotificationTemplateUUID);

    Boolean deleteCustomerNotificationTemplate(String customerNotificationTemplateUUID);
}
