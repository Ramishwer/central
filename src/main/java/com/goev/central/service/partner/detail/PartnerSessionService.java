package com.goev.central.service.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerSessionDto;

public interface PartnerSessionService {
    PaginatedResponseDto<PartnerSessionDto> getPartnerSessions(String partnerUUID);
    
    PartnerSessionDto getPartnerSessionDetails(String partnerUUID, String partnerSessionUUID);

    Boolean deletePartnerSession(String partnerUUID, String partnerSessionUUID);
}
