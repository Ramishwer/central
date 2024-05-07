package com.goev.central.service.partner.duty;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;

public interface PartnerDutyService {
    PaginatedResponseDto<PartnerDutyDto> getDuties(String partnerUUID);
    PartnerDutyDto createDuty(String partnerUUID,PartnerDutyDto partnerDutyDto);
    PartnerDutyDto updateDuty(String partnerUUID,String dutyUUID, PartnerDutyDto partnerDutyDto);
    PartnerDutyDto getDutyDetails(String partnerUUID,String dutyUUID);
    Boolean deleteDuty(String partnerUUID,String dutyUUID);

}
