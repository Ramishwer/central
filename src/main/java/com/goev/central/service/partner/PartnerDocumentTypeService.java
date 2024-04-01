package com.goev.central.service.partner;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.document.PartnerDocumentTypeDto;

public interface PartnerDocumentTypeService {
    PaginatedResponseDto<PartnerDocumentTypeDto> getDocumentTypes();
    PartnerDocumentTypeDto createDocumentType(PartnerDocumentTypeDto partnerDocumentTypeDto);
    PartnerDocumentTypeDto updateDocumentType(String documentTypeUUID, PartnerDocumentTypeDto partnerDocumentTypeDto);
    PartnerDocumentTypeDto getDocumentTypeDetails(String documentTypeUUID);
    Boolean deleteDocumentType(String documentTypeUUID);
}
