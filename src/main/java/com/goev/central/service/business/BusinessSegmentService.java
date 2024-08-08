package com.goev.central.service.business;

import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.business.BusinessSegmentMappingDto;
import com.goev.central.dto.common.PaginatedResponseDto;

import java.util.List;

public interface BusinessSegmentService {
    PaginatedResponseDto<BusinessSegmentDto> getSegments();

    BusinessSegmentDto createSegment(BusinessSegmentDto businessSegmentDto);

    BusinessSegmentDto updateSegment(String segmentUUID, BusinessSegmentDto businessSegmentDto);

    BusinessSegmentDto getSegmentDetails(String segmentUUID);

    Boolean deleteSegment(String segmentUUID);

    List<BusinessSegmentMappingDto> getVehicleSegmentMappings(String segmentUUID);

    BusinessSegmentMappingDto createVehicleSegmentMapping(String segmentUUID, BusinessSegmentMappingDto businessSegmentMappingDto);

    Boolean deleteVehicleSegmentMapping(String segmentUUID, String vehicleSegmentMappingUUID);

    List<BusinessSegmentMappingDto> getPartnerSegmentMappings(String segmentUUID);

    BusinessSegmentMappingDto createPartnerSegmentMapping(String segmentUUID, BusinessSegmentMappingDto businessSegmentMappingDto);

    Boolean deletePartnerSegmentMapping(String segmentUUID, String partnerSegmentMappingUUID);
}
