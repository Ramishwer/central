package com.goev.central.service.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;

public interface PartnerSegmentService {
    PaginatedResponseDto<PartnerSegmentDto> getSegments();

    PartnerSegmentDto createSegment(PartnerSegmentDto partnerSegmentDto);

    PartnerSegmentDto updateSegment(String segmentUUID, PartnerSegmentDto partnerSegmentDto);

    PartnerSegmentDto getSegmentDetails(String segmentUUID);

    Boolean deleteSegment(String segmentUUID);

}
