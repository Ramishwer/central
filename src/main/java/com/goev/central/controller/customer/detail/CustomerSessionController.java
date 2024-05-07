package com.goev.central.controller.customer.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerSessionDto;
import com.goev.central.service.customer.detail.CustomerSessionService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerSessionController {

    private final CustomerSessionService customerSessionService;

    @GetMapping("/customers/{customer-uuid}/sessions")
    public ResponseDto<PaginatedResponseDto<CustomerSessionDto>> getCustomerSessions(@PathVariable(value = "customer-uuid")String customerUUID,
            @RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerSessionService.getCustomerSessions(customerUUID));
    }

    

    @GetMapping("/customers/{customer-uuid}/sessions/{session-uuid}")
    public ResponseDto<CustomerSessionDto> getCustomerSessionDetails(@PathVariable(value = "customer-uuid")String customerUUID,
                                                                   @PathVariable(value = "session-uuid")String sessionUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerSessionService.getCustomerSessionDetails(customerUUID,sessionUUID));
    }
    
    @DeleteMapping("/customers/{customer-uuid}/sessions/{session-uuid}")
    public ResponseDto<Boolean> deleteCustomerSession(@PathVariable(value = "customer-uuid")String customerUUID,@PathVariable(value = "session-uuid")String sessionUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerSessionService.deleteCustomerSession(customerUUID,sessionUUID));
    }
}
