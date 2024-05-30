package com.goev.central.service.partner.detail;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDto;

import java.util.List;

public interface PartnerService {
    
    PaginatedResponseDto<PartnerViewDto> getPartners();
    Boolean deletePartner(String partnerUUID);

    PaginatedResponseDto<PartnerDto> getPartnerStatus();

}
