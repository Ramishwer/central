package com.goev.central.controller.customer.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerDto;
import com.goev.central.service.customer.detail.CustomerService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerController {


    private final CustomerService customerService;


    @GetMapping("/customers")
    public ResponseDto<PaginatedResponseDto<CustomerDto>> getCustomers(@RequestParam(value = "count",required = false) Integer count,
                                                                       @RequestParam(value = "start", required = false) Integer start,
                                                                       @RequestParam(value = "from", required = false) Long from,
                                                                       @RequestParam(value = "to", required = false) Long to,
                                                                       @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerService.getCustomers());
    }
}
