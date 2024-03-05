package com.goev.central.service;

import com.goev.central.dto.partner.PartnerDetailsDto;

public interface PartnerService {
    PartnerDetailsDto createPartner(PartnerDetailsDto partnerDto);
    PartnerDetailsDto updatePartner(String partnerUUID, PartnerDetailsDto credentials);
    PartnerDetailsDto getPartnerDetails(String partnerUUID);
    Boolean deletePartner(String partnerUUID);

}
