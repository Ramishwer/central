package com.goev.central.controller.customer.segment;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.segment.CustomerSegmentDto;
import com.goev.central.service.customer.segment.CustomerSegmentService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerSegmentController {

    private final CustomerSegmentService customerSegmentService;

    @GetMapping("/segments")
    public ResponseDto<PaginatedResponseDto<CustomerSegmentDto>> getCustomerSegments(@RequestParam(value = "count", required = false) Integer count,
                                                                                     @RequestParam(value = "start", required = false) Integer start,
                                                                                     @RequestParam(value = "from", required = false) Long from,
                                                                                     @RequestParam(value = "to", required = false) Long to,
                                                                                     @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerSegmentService.getCustomerSegments());
    }


    @GetMapping("/segments/{segment-uuid}")
    public ResponseDto<CustomerSegmentDto> getCustomerSegmentDetails(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerSegmentService.getCustomerSegmentDetails(segmentUUID));
    }


    @PostMapping("/segments")
    public ResponseDto<CustomerSegmentDto> createCustomerSegment(@RequestBody CustomerSegmentDto customerSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerSegmentService.createCustomerSegment(customerSegmentDto));
    }

    @PutMapping("/segments/{segment-uuid}")
    public ResponseDto<CustomerSegmentDto> updateCustomerSegment(@PathVariable(value = "segment-uuid") String segmentUUID, @RequestBody CustomerSegmentDto customerSegmentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerSegmentService.updateCustomerSegment(segmentUUID, customerSegmentDto));
    }

    @DeleteMapping("/segments/{segment-uuid}")
    public ResponseDto<Boolean> deleteCustomerSegment(@PathVariable(value = "segment-uuid") String segmentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerSegmentService.deleteCustomerSegment(segmentUUID));
    }
}
