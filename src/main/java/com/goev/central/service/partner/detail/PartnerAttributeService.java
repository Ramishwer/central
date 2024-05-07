package com.goev.central.service.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerAttributeDto;

public interface PartnerAttributeService {
    PaginatedResponseDto<PartnerAttributeDto> getPartnerAttributes(String partnerUUID);

    PartnerAttributeDto createPartnerAttribute(String partnerUUID, PartnerAttributeDto partnerAttributeDto);

    PartnerAttributeDto updatePartnerAttribute(String partnerUUID, String partnerAttributeUUID, PartnerAttributeDto partnerAttributeDto);

    PartnerAttributeDto getPartnerAttributeDetails(String partnerUUID, String partnerAttributeUUID);

    Boolean deletePartnerAttribute(String partnerUUID, String partnerAttributeUUID);
}
