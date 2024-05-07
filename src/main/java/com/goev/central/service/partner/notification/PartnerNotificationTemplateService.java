package com.goev.central.service.partner.notification;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.notification.PartnerNotificationTemplateDto;

public interface PartnerNotificationTemplateService {
    PaginatedResponseDto<PartnerNotificationTemplateDto> getPartnerNotificationTemplates();

    PartnerNotificationTemplateDto createPartnerNotificationTemplate(PartnerNotificationTemplateDto partnerNotificationTemplateDto);

    PartnerNotificationTemplateDto updatePartnerNotificationTemplate(String partnerNotificationTemplateUUID, PartnerNotificationTemplateDto partnerNotificationTemplateDto);

    PartnerNotificationTemplateDto getPartnerNotificationTemplateDetails(String partnerNotificationTemplateUUID);

    Boolean deletePartnerNotificationTemplate(String partnerNotificationTemplateUUID);
}
