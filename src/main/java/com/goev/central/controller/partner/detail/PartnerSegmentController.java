package com.goev.central.controller.partner.detail;

import com.goev.central.dto.business.BusinessSegmentMappingDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;
import com.goev.central.dto.partner.detail.PartnerSegmentMappingDto;
import com.goev.central.dto.partner.duty.PartnerShiftMappingDto;
import com.goev.central.service.partner.detail.PartnerSegmentService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerSegmentController {
    private final PartnerSegmentService partnerSegmentService;

    @GetMapping("/partners/segments")
    public ResponseDto<PaginatedResponseDto<PartnerSegmentDto>> getSegments() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.getSegments());
    }

    @PostMapping("/partners/segments")
    public ResponseDto<PartnerSegmentDto> createSegment(@RequestBody PartnerSegmentDto partnerSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.createSegment(partnerSegmentDto));
    }

    @PutMapping("/partners/segments/{segment-uuid}")
    public ResponseDto<PartnerSegmentDto> updateSegment(@PathVariable(value = "segment-uuid") String segmentUUID, @RequestBody PartnerSegmentDto partnerSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.updateSegment(segmentUUID, partnerSegmentDto));
    }

    @GetMapping("/partners/segments/{segment-uuid}")
    public ResponseDto<PartnerSegmentDto> getSegmentDetails(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.getSegmentDetails(segmentUUID));
    }

    @GetMapping("/partners/{partner-uuid}/segments")
    public ResponseDto<List<PartnerSegmentDto>> getSegmentsForPartner(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.getSegmentsForPartner(partnerUUID));
    }

    @DeleteMapping("/partners/segments/{segment-uuid}")
    public ResponseDto<Boolean> deleteSegment(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.deleteSegment(segmentUUID));
    }


    @GetMapping("/partners/segments/{segment-uuid}/partner-mappings")
    public ResponseDto<List<PartnerSegmentMappingDto>> getSegmentMappings(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.getPartnerMappings(segmentUUID));
    }

    @PostMapping("/partners/segments/{segment-uuid}/partner-mappings")
    public ResponseDto<PartnerSegmentMappingDto> createPartnerMapping(@PathVariable(value = "segment-uuid") String segmentUUID, @RequestBody PartnerSegmentMappingDto partnerSegmentMappingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.createPartnerMapping(segmentUUID, partnerSegmentMappingDto));
    }

    @DeleteMapping("/partners/segments/{segment-uuid}/partner-mappings/{partner-segment-mapping-uuid}")
    public ResponseDto<Boolean> deletePartnerMapping(@PathVariable(value = "segment-uuid") String segmentUUID,@PathVariable(value = "partner-segment-mapping-uuid") String partnerSegmentMappingUUID)  {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.deletePartnerMapping(segmentUUID, partnerSegmentMappingUUID));
    }


    @GetMapping("/partners/segments/{segment-uuid}/vehicle-segment-mappings")
    public ResponseDto<List<PartnerSegmentMappingDto>> getVehicleSegmentMappings(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.getVehicleSegmentMappings(segmentUUID));
    }

    @PostMapping("/partners/segments/{segment-uuid}/vehicle-segment-mappings")
    public ResponseDto<PartnerSegmentMappingDto> createVehicleSegmentMapping(@PathVariable(value = "segment-uuid") String segmentUUID, @RequestBody PartnerSegmentMappingDto partnerSegmentMappingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.createVehicleSegmentMapping(segmentUUID, partnerSegmentMappingDto));
    }

    @DeleteMapping("/partners/segments/{segment-uuid}/vehicle-segment-mappings/{vehicle-segment-mapping-uuid}")
    public ResponseDto<Boolean> deleteVehicleSegmentMapping(@PathVariable(value = "segment-uuid") String segmentUUID,@PathVariable(value = "vehicle-segment-mapping-uuid") String vehicleSegmentMappingUUID)  {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.deleteVehicleSegmentMapping(segmentUUID, vehicleSegmentMappingUUID));
    }

}
