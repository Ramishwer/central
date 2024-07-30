package com.goev.central.service.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;
import com.goev.central.dto.partner.detail.PartnerSegmentMappingDto;
import com.goev.central.dto.partner.duty.PartnerShiftMappingDto;

import java.util.List;

public interface PartnerSegmentService {
    PaginatedResponseDto<PartnerSegmentDto> getSegments();

    PartnerSegmentDto createSegment(PartnerSegmentDto partnerSegmentDto);

    PartnerSegmentDto updateSegment(String segmentUUID, PartnerSegmentDto partnerSegmentDto);

    PartnerSegmentDto getSegmentDetails(String segmentUUID);

    Boolean deleteSegment(String segmentUUID);

    PartnerSegmentMappingDto createPartnerMapping(String partnerUUID, PartnerSegmentMappingDto partnerSegmentMappingDto);

    Boolean deletePartnerMapping(String segmentUUID, String partnerSegmentMappingUUID);

    List<PartnerSegmentMappingDto> getPartnerMappings(String segmentUUID);
}
