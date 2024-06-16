package com.goev.central.service.partner.duty;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;

public interface PartnerShiftService {
    PaginatedResponseDto<PartnerShiftDto> getShifts(String partnerUUID);

    PartnerShiftDto createShift(String partnerUUID, PartnerShiftDto partnerShiftDto);

    PartnerShiftDto updateShift(String partnerUUID, String shiftUUID, PartnerShiftDto partnerShiftDto);

    PartnerShiftDto getShiftDetails(String partnerUUID, String shiftUUID);

    Boolean deleteShift(String partnerUUID, String shiftUUID);

}
