package com.goev.central.service.partner;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDetailsDto;
import com.goev.central.dto.partner.detail.PartnerDto;

public interface PartnerService {
    PartnerDetailsDto createPartner(PartnerDetailsDto partnerDto);
    PartnerDetailsDto updatePartner(String partnerUUID, PartnerDetailsDto credentials);
    PartnerDetailsDto getPartnerDetails(String partnerUUID);
    Boolean deletePartner(String partnerUUID);
    PaginatedResponseDto<PartnerViewDto> getPartners();
}
