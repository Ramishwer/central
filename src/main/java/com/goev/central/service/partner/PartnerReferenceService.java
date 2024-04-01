package com.goev.central.service.partner;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerReferenceDto;

public interface PartnerReferenceService {
    PaginatedResponseDto<PartnerReferenceDto> getReferences(String partnerUUID);
    PartnerReferenceDto createReference(String partnerUUID,PartnerReferenceDto partnerReferenceDto);
    PartnerReferenceDto updateReference(String partnerUUID,String referenceUUID, PartnerReferenceDto partnerReferenceDto);
    PartnerReferenceDto getReferenceDetails(String partnerUUID,String referenceUUID);
    Boolean deleteReference(String partnerUUID,String referenceUUID);

}
