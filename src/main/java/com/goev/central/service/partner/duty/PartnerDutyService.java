package com.goev.central.service.partner.duty;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;

public interface PartnerDutyService {
    PaginatedResponseDto<PartnerDutyDto> getDuties(String status);

    PartnerDutyDto getDutyDetails(String partnerUUID, String dutyUUID);

}
