package com.goev.central.controller.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentMappingDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentDto;
import com.goev.central.service.vehicle.detail.VehicleSegmentService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleSegmentController {
    private final VehicleSegmentService vehicleSegmentService;

    @GetMapping("/vehicles/segments")
    public ResponseDto<PaginatedResponseDto<VehicleSegmentDto>> getSegments() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.getSegments());
    }

    @PostMapping("/vehicles/segments")
    public ResponseDto<VehicleSegmentDto> createSegment(@RequestBody VehicleSegmentDto vehicleSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.createSegment(vehicleSegmentDto));
    }

    @PutMapping("/vehicles/segments/{segment-uuid}")
    public ResponseDto<VehicleSegmentDto> updateSegment(@PathVariable(value = "segment-uuid") String segmentUUID, @RequestBody VehicleSegmentDto vehicleSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.updateSegment(segmentUUID, vehicleSegmentDto));
    }

    @GetMapping("/vehicles/segments/{segment-uuid}")
    public ResponseDto<VehicleSegmentDto> getSegmentDetails(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.getSegmentDetails(segmentUUID));
    }
    @GetMapping("/vehicles/{vehicle-uuid}/segments")
    public ResponseDto<List<VehicleSegmentDto>> getSegmentsForVehicle(@PathVariable(value = "vehicle-uuid") String vehicleUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.getSegmentsForVehicle(vehicleUUID));
    }

    @DeleteMapping("/vehicles/segments/{segment-uuid}")
    public ResponseDto<Boolean> deleteSegment(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.deleteSegment(segmentUUID));
    }


    @GetMapping("/vehicles/segments/{segment-uuid}/vehicle-mappings")
    public ResponseDto<List<VehicleSegmentMappingDto>> getSegmentMappings(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.getVehicleMappings(segmentUUID));
    }

    @PostMapping("/vehicles/segments/{segment-uuid}/vehicle-mappings")
    public ResponseDto<VehicleSegmentMappingDto> createVehicleMapping(@PathVariable(value = "segment-uuid") String segmentUUID, @RequestBody VehicleSegmentMappingDto vehicleSegmentMappingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.createVehicleMapping(segmentUUID, vehicleSegmentMappingDto));
    }

    @DeleteMapping("/vehicles/segments/{segment-uuid}/vehicle-mappings/{vehicle-segment-mapping-uuid}")
    public ResponseDto<Boolean> deleteVehicleMapping(@PathVariable(value = "segment-uuid") String segmentUUID,@PathVariable(value = "vehicle-segment-mapping-uuid") String vehicleSegmentMappingUUID)  {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.deleteVehicleMapping(segmentUUID, vehicleSegmentMappingUUID));
    }
}
