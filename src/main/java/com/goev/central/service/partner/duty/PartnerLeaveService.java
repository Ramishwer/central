package com.goev.central.service.partner.duty;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerLeaveDto;

public interface PartnerLeaveService {
    PaginatedResponseDto<PartnerLeaveDto> getLeaves(String partnerUUID);

    PartnerLeaveDto createLeave(String partnerUUID, PartnerLeaveDto partnerLeaveDto);

    PartnerLeaveDto updateLeave(String partnerUUID, String leaveUUID, PartnerLeaveDto partnerLeaveDto);

    PartnerLeaveDto getLeaveDetails(String partnerUUID, String leaveUUID);

    Boolean deleteLeave(String partnerUUID, String leaveUUID);

}
