package com.goev.central.service.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerReferenceDto;

import java.util.List;

public interface PartnerReferenceService {
    PaginatedResponseDto<PartnerReferenceDto> getReferences(String partnerUUID);
    PartnerReferenceDto createReference(String partnerUUID,PartnerReferenceDto partnerReferenceDto);
    PartnerReferenceDto updateReference(String partnerUUID,String referenceUUID, PartnerReferenceDto partnerReferenceDto);
    PartnerReferenceDto getReferenceDetails(String partnerUUID,String referenceUUID);
    Boolean deleteReference(String partnerUUID,String referenceUUID);

    List<PartnerReferenceDto> bulkCreateReference(String partnerUUID, List<PartnerReferenceDto> partnerReferenceDto);
}
