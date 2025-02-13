package com.goev.central.service.partner.document;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.document.PartnerDocumentDto;
import com.goev.central.enums.DocumentStatus;

import java.util.List;

public interface PartnerDocumentService {
    PaginatedResponseDto<PartnerDocumentDto> getDocuments(String partnerUUID);

    PartnerDocumentDto createDocument(String partnerUUID, PartnerDocumentDto partnerDocumentDto);

    PartnerDocumentDto updateDocument(String partnerUUID, String documentUUID, PartnerDocumentDto partnerDocumentDto);

    PartnerDocumentDto getDocumentDetails(String partnerUUID, String documentUUID);

    Boolean deleteDocument(String partnerUUID, String documentUUID);

    DocumentStatus isAllMandatoryDocumentsUploaded(Integer partnerId);

    List<PartnerDocumentDto> bulkCreateDocument(String partnerUUID, List<PartnerDocumentDto> partnerDocumentDto);

    PartnerDocumentDto updateDocumentStatus(String partnerUUID, String documentUUID, DocumentStatus status);
}
