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
    public ResponseDto<PaginatedResponseDto<CustomerDto>> getCustomers() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerService.getCustomers());
    }
}
