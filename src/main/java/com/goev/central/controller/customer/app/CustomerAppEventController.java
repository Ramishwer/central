package com.goev.central.controller.customer.app;


import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.app.CustomerAppEventDto;
import com.goev.central.service.customer.app.CustomerAppEventService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerAppEventController {

    private final CustomerAppEventService customerAppEventService;

    @GetMapping("/customers/{customer-uuid}/app-events")
    public ResponseDto<PaginatedResponseDto<CustomerAppEventDto>> getCustomerAppEvents(@PathVariable("customer-uuid") String customerUUID,
                                                                                       @RequestParam("count") Integer count,
                                                                                       @RequestParam("start") Integer start,
                                                                                       @RequestParam("from") Long from,
                                                                                       @RequestParam("to") Long to,
                                                                                       @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppEventService.getCustomerAppEvents(customerUUID));
    }


    @GetMapping("/customers/{customer-uuid}/app-events/{app-event-uuid}")
    public ResponseDto<CustomerAppEventDto> getCustomerAppEventDetails(@PathVariable("customer-uuid") String customerUUID, @PathVariable("app-event-uuid") String appEventUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppEventService.getCustomerAppEventDetails(customerUUID, appEventUUID));
    }


}
