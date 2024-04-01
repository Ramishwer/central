package com.goev.central.controller.business;

import com.goev.central.dto.common.PaginatedResponseDto;

import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.service.business.BusinessSegmentService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/business-management")
@AllArgsConstructor
public class BusinessSegmentController {
    private final BusinessSegmentService businessSegmentService;

    @GetMapping("/segments")
    public ResponseDto<PaginatedResponseDto<BusinessSegmentDto>> getSegments(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, businessSegmentService.getSegments());
    }
    @PostMapping("/segments")
    public ResponseDto<BusinessSegmentDto> createSegment(@RequestBody BusinessSegmentDto businessSegmentDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, businessSegmentService.createSegment(businessSegmentDto));
    }

    @PutMapping("/segments/{segment-uuid}")
    public ResponseDto<BusinessSegmentDto> updateSegment(@PathVariable(value = "segment-uuid")String segmentUUID, @RequestBody BusinessSegmentDto businessSegmentDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, businessSegmentService.updateSegment(segmentUUID,businessSegmentDto));
    }

    @GetMapping("/segments/{segment-uuid}")
    public ResponseDto<BusinessSegmentDto> getSegmentDetails(@PathVariable(value = "segment-uuid")String segmentUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, businessSegmentService.getSegmentDetails(segmentUUID));
    }

    @DeleteMapping("/segments/{segment-uuid}")
    public ResponseDto<Boolean> deleteSegment(@PathVariable(value = "segment-uuid")String segmentUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, businessSegmentService.deleteSegment(segmentUUID));
    }
}
