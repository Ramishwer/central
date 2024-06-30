package com.goev.central.controller.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentDto;
import com.goev.central.service.vehicle.detail.VehicleSegmentService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleSegmentController {
    private final VehicleSegmentService vehicleSegmentService;

    @GetMapping("/segments")
    public ResponseDto<PaginatedResponseDto<VehicleSegmentDto>> getSegments() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.getSegments());
    }

    @PostMapping("/segments")
    public ResponseDto<VehicleSegmentDto> createSegment(@RequestBody VehicleSegmentDto vehicleSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.createSegment(vehicleSegmentDto));
    }

    @PutMapping("/segments/{segment-uuid}")
    public ResponseDto<VehicleSegmentDto> updateSegment(@PathVariable(value = "segment-uuid") String segmentUUID, @RequestBody VehicleSegmentDto vehicleSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.updateSegment(segmentUUID, vehicleSegmentDto));
    }

    @GetMapping("/segments/{segment-uuid}")
    public ResponseDto<VehicleSegmentDto> getSegmentDetails(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.getSegmentDetails(segmentUUID));
    }

    @DeleteMapping("/segments/{segment-uuid}")
    public ResponseDto<Boolean> deleteSegment(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleSegmentService.deleteSegment(segmentUUID));
    }
}
