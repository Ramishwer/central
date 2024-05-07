package com.goev.central.service.partner.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppStringDto;

public interface PartnerAppStringService {
    PaginatedResponseDto<PartnerAppStringDto> getPartnerAppStrings();

    PartnerAppStringDto createPartnerAppString(PartnerAppStringDto partnerAppStringDto);

    PartnerAppStringDto updatePartnerAppString(String partnerAppStringUUID, PartnerAppStringDto partnerAppStringDto);

    PartnerAppStringDto getPartnerAppStringDetails(String partnerAppStringUUID);

    Boolean deletePartnerAppString(String partnerAppStringUUID);
}
