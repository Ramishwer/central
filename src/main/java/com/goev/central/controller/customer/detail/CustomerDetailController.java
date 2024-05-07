package com.goev.central.controller.customer.detail;

import com.goev.central.dto.customer.detail.CustomerDetailDto;
import com.goev.central.dto.customer.detail.CustomerDto;
import com.goev.central.service.customer.detail.CustomerDetailService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerDetailController {


    private final CustomerDetailService customerDetailService;

    @PostMapping("/customers/details")
    public ResponseDto<CustomerDetailDto> createCustomer(@RequestBody CustomerDetailDto customerDetailDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerDetailService.createCustomer(customerDetailDto));
    }

    @GetMapping("/customers/{customer-uuid}/details")
    public ResponseDto<CustomerDetailDto> getCustomerDetails(@PathVariable(value = "customer-uuid") String customerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerDetailService.getCustomerDetails(customerUUID));
    }

}
