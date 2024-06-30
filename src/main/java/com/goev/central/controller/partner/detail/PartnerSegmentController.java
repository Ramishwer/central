package com.goev.central.controller.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;
import com.goev.central.service.partner.detail.PartnerSegmentService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerSegmentController {
    private final PartnerSegmentService partnerSegmentService;

    @GetMapping("/segments")
    public ResponseDto<PaginatedResponseDto<PartnerSegmentDto>> getSegments() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.getSegments());
    }

    @PostMapping("/segments")
    public ResponseDto<PartnerSegmentDto> createSegment(@RequestBody PartnerSegmentDto partnerSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.createSegment(partnerSegmentDto));
    }

    @PutMapping("/segments/{segment-uuid}")
    public ResponseDto<PartnerSegmentDto> updateSegment(@PathVariable(value = "segment-uuid") String segmentUUID, @RequestBody PartnerSegmentDto partnerSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.updateSegment(segmentUUID, partnerSegmentDto));
    }

    @GetMapping("/segments/{segment-uuid}")
    public ResponseDto<PartnerSegmentDto> getSegmentDetails(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.getSegmentDetails(segmentUUID));
    }

    @DeleteMapping("/segments/{segment-uuid}")
    public ResponseDto<Boolean> deleteSegment(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSegmentService.deleteSegment(segmentUUID));
    }
}
