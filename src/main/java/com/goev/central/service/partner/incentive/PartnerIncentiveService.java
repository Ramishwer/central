package com.goev.central.service.partner.incentive;

import com.goev.central.dto.partner.incentive.PartnerIncentiveMappingDto;

import java.util.List;

public interface PartnerIncentiveService {
    List<PartnerIncentiveMappingDto> getIncentiveModelMappings(String partnerUUID);

    Boolean deleteIncentiveModelMapping(String partnerUUID, String partnerIncentiveModelMappingUUID);

    PartnerIncentiveMappingDto createIncentiveModelMapping(String partnerUUID, PartnerIncentiveMappingDto partnerIncentiveMappingDto);
}
