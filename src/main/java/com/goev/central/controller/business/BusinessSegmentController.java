package com.goev.central.controller.business;

import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.business.BusinessSegmentMappingDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentMappingDto;
import com.goev.central.service.business.BusinessSegmentService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/business-management")
@AllArgsConstructor
public class BusinessSegmentController {
    private final BusinessSegmentService businessSegmentService;

    @GetMapping("/segments")
    public ResponseDto<PaginatedResponseDto<BusinessSegmentDto>> getSegments(@RequestParam(value = "count", required = false) Integer count,
                                                                             @RequestParam(value = "start", required = false) Integer start,
                                                                             @RequestParam(value = "from", required = false) Long from,
                                                                             @RequestParam(value = "to", required = false) Long to,
                                                                             @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, businessSegmentService.getSegments());
    }

    @PostMapping("/segments")
    public ResponseDto<BusinessSegmentDto> createSegment(@RequestBody BusinessSegmentDto businessSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, businessSegmentService.createSegment(businessSegmentDto));
    }

    @PutMapping("/segments/{segment-uuid}")
    public ResponseDto<BusinessSegmentDto> updateSegment(@PathVariable(value = "segment-uuid") String segmentUUID, @RequestBody BusinessSegmentDto businessSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, businessSegmentService.updateSegment(segmentUUID, businessSegmentDto));
    }

    @GetMapping("/segments/{segment-uuid}")
    public ResponseDto<BusinessSegmentDto> getSegmentDetails(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, businessSegmentService.getSegmentDetails(segmentUUID));
    }

    @DeleteMapping("/segments/{segment-uuid}")
    public ResponseDto<Boolean> deleteSegment(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, businessSegmentService.deleteSegment(segmentUUID));
    }


    @GetMapping("/segments/{segment-uuid}/vehicle-segment-mappings")
    public ResponseDto<List<BusinessSegmentMappingDto>> getVehicleSegmentMappings(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, businessSegmentService.getVehicleSegmentMappings(segmentUUID));
    }

    @PostMapping("/segments/{segment-uuid}/vehicle-segment-mappings")
    public ResponseDto<BusinessSegmentMappingDto> createVehicleSegmentMapping(@PathVariable(value = "segment-uuid") String segmentUUID, @RequestBody BusinessSegmentMappingDto businessSegmentMappingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, businessSegmentService.createVehicleSegmentMapping(segmentUUID, businessSegmentMappingDto));
    }

    @DeleteMapping("/segments/{segment-uuid}/vehicle-segment-mappings/{vehicle-segment-mapping-uuid}")
    public ResponseDto<Boolean> deleteVehicleSegmentMapping(@PathVariable(value = "segment-uuid") String segmentUUID,@PathVariable(value = "vehicle-segment-mapping-uuid") String vehicleSegmentMappingUUID)  {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, businessSegmentService.deleteVehicleSegmentMapping(segmentUUID, vehicleSegmentMappingUUID));
    }

    @GetMapping("/segments/{segment-uuid}/partner-segment-mappings")
    public ResponseDto<List<BusinessSegmentMappingDto>> getPartnerSegmentMappings(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, businessSegmentService.getPartnerSegmentMappings(segmentUUID));
    }

    @PostMapping("/segments/{segment-uuid}/partner-segment-mappings")
    public ResponseDto<BusinessSegmentMappingDto> createPartnerSegmentMapping(@PathVariable(value = "segment-uuid") String segmentUUID, @RequestBody BusinessSegmentMappingDto businessSegmentMappingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, businessSegmentService.createPartnerSegmentMapping(segmentUUID, businessSegmentMappingDto));
    }

    @DeleteMapping("/segments/{segment-uuid}/partner-segment-mappings/{partner-segment-mapping-uuid}")
    public ResponseDto<Boolean> deletePartnerSegmentMapping(@PathVariable(value = "segment-uuid") String segmentUUID,@PathVariable(value = "partner-segment-mapping-uuid") String partnerSegmentMappingUUID)  {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, businessSegmentService.deletePartnerSegmentMapping(segmentUUID, partnerSegmentMappingUUID));
    }
}
