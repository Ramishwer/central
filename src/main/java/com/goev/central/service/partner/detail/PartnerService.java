package com.goev.central.service.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerActionDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.dto.partner.detail.PartnerTrackingDto;

public interface PartnerService {

    PaginatedResponseDto<PartnerViewDto> getPartners();

    PaginatedResponseDto<PartnerViewDto> getPartners(String onboardingStatus);

    Boolean deletePartner(String partnerUUID);

    PartnerDto updatePartner(String partnerUUID, PartnerActionDto actionDto);

    PaginatedResponseDto<PartnerDto> getPartnerStatuses(String status);

    PartnerDto getPartnerStatus(String partnerUUID);

    PaginatedResponseDto<PartnerTrackingDto> getPartnerTrackings();

}
