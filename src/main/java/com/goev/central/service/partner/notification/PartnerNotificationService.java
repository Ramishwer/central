package com.goev.central.service.partner.notification;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.notification.PartnerNotificationDto;

public interface PartnerNotificationService {
    PaginatedResponseDto<PartnerNotificationDto> getPartnerNotifications(String partnerUUID);

    PartnerNotificationDto getPartnerNotificationDetails(String partnerUUID, String partnerNotificationUUID);

}
