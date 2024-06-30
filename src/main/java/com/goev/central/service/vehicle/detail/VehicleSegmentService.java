package com.goev.central.service.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentDto;

public interface VehicleSegmentService {
    PaginatedResponseDto<VehicleSegmentDto> getSegments();

    VehicleSegmentDto createSegment(VehicleSegmentDto vehicleSegmentDto);

    VehicleSegmentDto updateSegment(String segmentUUID, VehicleSegmentDto vehicleSegmentDto);

    VehicleSegmentDto getSegmentDetails(String segmentUUID);

    Boolean deleteSegment(String segmentUUID);

}
