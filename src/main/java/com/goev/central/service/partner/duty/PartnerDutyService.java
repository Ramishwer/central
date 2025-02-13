package com.goev.central.service.partner.duty;

import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;

public interface PartnerDutyService {
    PaginatedResponseDto<PartnerDutyDto> getDuties(String status, PageDto page, FilterDto filter);

    PartnerDutyDto getDutyDetails(String partnerUUID, String dutyUUID);

}
