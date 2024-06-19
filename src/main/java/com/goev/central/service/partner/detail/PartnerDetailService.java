package com.goev.central.service.partner.detail;


import com.goev.central.dto.partner.detail.PartnerDetailDto;

public interface PartnerDetailService {
    PartnerDetailDto createPartner(PartnerDetailDto partnerDto);

    PartnerDetailDto getPartnerDetails(String partnerUUID);

    PartnerDetailDto updatePartner(String partnerUUID, PartnerDetailDto partnerDetailDto);

    boolean updateOnboardingStatus(String partnerUUID);
}
