package com.goev.central.service.partner.duty;

import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.dto.partner.duty.PartnerShiftMappingDto;

import java.util.List;

public interface PartnerShiftService {
    PaginatedResponseDto<PartnerShiftDto> getShifts(String status, PageDto page);

    PartnerShiftMappingDto createShiftMapping(String partnerUUID, PartnerShiftMappingDto partnerShiftDto);
    Boolean deleteShiftMapping(String partnerUUID, String partnerShiftMappingUUID);

    PartnerShiftDto updateShift(String partnerUUID, String shiftUUID, PartnerShiftDto partnerShiftDto);

    PartnerShiftDto getShiftDetails(String partnerUUID, String shiftUUID);

    Boolean deleteShift(String partnerUUID, String shiftUUID);

    List<PartnerShiftMappingDto> getShiftMappings(String partnerUUID);
}
