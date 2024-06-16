package com.goev.central.controller.customer.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.app.CustomerAppStringDto;
import com.goev.central.service.customer.app.CustomerAppStringService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerAppStringController {

    private final CustomerAppStringService customerAppStringService;

    @GetMapping("/app/strings")
    public ResponseDto<PaginatedResponseDto<CustomerAppStringDto>> getCustomerAppStrings(@RequestParam("count") Integer count,
                                                                                         @RequestParam("start") Integer start,
                                                                                         @RequestParam("from") Long from,
                                                                                         @RequestParam("to") Long to,
                                                                                         @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppStringService.getCustomerAppStrings());
    }


    @GetMapping("/app/strings/{app-string-uuid}")
    public ResponseDto<CustomerAppStringDto> getCustomerAppStringDetails(@PathVariable(value = "app-string-uuid") String appStringUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppStringService.getCustomerAppStringDetails(appStringUUID));
    }


    @PostMapping("/app/strings")
    public ResponseDto<CustomerAppStringDto> createCustomerAppString(@RequestBody CustomerAppStringDto customerAppStringDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppStringService.createCustomerAppString(customerAppStringDto));
    }

    @PutMapping("/app/strings/{app-string-uuid}")
    public ResponseDto<CustomerAppStringDto> updateCustomerAppString(@PathVariable(value = "app-string-uuid") String appStringUUID, @RequestBody CustomerAppStringDto customerAppStringDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppStringService.updateCustomerAppString(appStringUUID, customerAppStringDto));
    }

    @DeleteMapping("/app/strings/{app-string-uuid}")
    public ResponseDto<Boolean> deleteCustomerAppString(@PathVariable(value = "app-string-uuid") String appStringUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppStringService.deleteCustomerAppString(appStringUUID));
    }
}
