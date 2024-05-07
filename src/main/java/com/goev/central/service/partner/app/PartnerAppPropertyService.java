package com.goev.central.service.partner.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppPropertyDto;

public interface PartnerAppPropertyService {
    PaginatedResponseDto<PartnerAppPropertyDto> getPartnerAppProperties();

    PartnerAppPropertyDto createPartnerAppProperty(PartnerAppPropertyDto partnerAppPropertyDto);

    PartnerAppPropertyDto updatePartnerAppProperty(String partnerAppPropertyUUID, PartnerAppPropertyDto partnerAppPropertyDto);

    PartnerAppPropertyDto getPartnerAppPropertyDetails(String partnerAppPropertyUUID);

    Boolean deletePartnerAppProperty(String partnerAppPropertyUUID);
}
