package com.goev.central.service.business;

import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface BusinessSegmentService {
    PaginatedResponseDto<BusinessSegmentDto> getSegments();

    BusinessSegmentDto createSegment(BusinessSegmentDto businessSegmentDto);

    BusinessSegmentDto updateSegment(String segmentUUID, BusinessSegmentDto businessSegmentDto);

    BusinessSegmentDto getSegmentDetails(String segmentUUID);

    Boolean deleteSegment(String segmentUUID);
}
