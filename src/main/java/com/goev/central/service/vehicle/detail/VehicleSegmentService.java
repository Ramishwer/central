package com.goev.central.service.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentMappingDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentDto;

import java.util.List;

public interface VehicleSegmentService {
    PaginatedResponseDto<VehicleSegmentDto> getSegments();

    VehicleSegmentDto createSegment(VehicleSegmentDto vehicleSegmentDto);

    VehicleSegmentDto updateSegment(String segmentUUID, VehicleSegmentDto vehicleSegmentDto);

    VehicleSegmentDto getSegmentDetails(String segmentUUID);

    Boolean deleteSegment(String segmentUUID);

    VehicleSegmentMappingDto createVehicleMapping(String vehicleUUID, VehicleSegmentMappingDto vehicleSegmentMappingDto);

    Boolean deleteVehicleMapping(String segmentUUID, String vehicleSegmentMappingUUID);

    List<VehicleSegmentMappingDto> getVehicleMappings(String segmentUUID);

}
