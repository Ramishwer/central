package com.goev.central.service;

import com.goev.central.dto.partner.PartnerDetailsDto;
import com.goev.central.dto.partner.PartnerDto;

import java.util.List;

public interface PartnerService {
    PartnerDetailsDto createPartner(PartnerDetailsDto partnerDto);
    PartnerDetailsDto updatePartner(String partnerUUID, PartnerDetailsDto credentials);
    PartnerDetailsDto getPartnerDetails(String partnerUUID);
    Boolean deletePartner(String partnerUUID);
    List<PartnerDto> getPartners();
}
