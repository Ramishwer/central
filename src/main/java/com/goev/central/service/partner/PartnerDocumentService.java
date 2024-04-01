package com.goev.central.service.partner;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.document.PartnerDocumentDto;

public interface PartnerDocumentService {
    PaginatedResponseDto<PartnerDocumentDto> getDocuments(String partnerUUID);
    PartnerDocumentDto createDocument(String partnerUUID,PartnerDocumentDto partnerDocumentDto);
    PartnerDocumentDto updateDocument(String partnerUUID,String documentUUID, PartnerDocumentDto partnerDocumentDto);
    PartnerDocumentDto getDocumentDetails(String partnerUUID,String documentUUID);
    Boolean deleteDocument(String partnerUUID,String documentUUID);

}
