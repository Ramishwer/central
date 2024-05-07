package com.goev.central.service.partner.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppEventDto;

public interface PartnerAppEventService {
    PaginatedResponseDto<PartnerAppEventDto> getPartnerAppEvents(String partnerUUID);

    PartnerAppEventDto getPartnerAppEventDetails(String partnerUUID, String appEventUUID);
}
