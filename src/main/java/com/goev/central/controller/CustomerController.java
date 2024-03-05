package com.goev.central.controller;

import com.goev.central.dto.customer.CustomerDetailsDto;
import com.goev.central.dto.customer.CustomerDto;
import com.goev.central.service.CustomerService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/customers")
    public ResponseDto<List<CustomerDto>> getCustomers(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerService.getCustomers());
    }
    @PostMapping("/customers")
    public ResponseDto<CustomerDetailsDto> createCustomer(@RequestBody CustomerDetailsDto customerDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerService.createCustomer(customerDto));
    }

    @PutMapping("/customers/{customer-uuid}")
    public ResponseDto<CustomerDetailsDto> updateCustomer(@PathVariable(value = "customer-uuid")String customerUUID, @RequestBody CustomerDetailsDto customerDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerService.updateCustomer(customerUUID,customerDto));
    }

    @GetMapping("/customers/{customer-uuid}")
    public ResponseDto<CustomerDetailsDto> getCustomerDetails(@PathVariable(value = "customer-uuid")String customerUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerService.getCustomerDetails(customerUUID));
    }

    @DeleteMapping("/customers/{customer-uuid}")
    public ResponseDto<Boolean> deleteCustomer(@PathVariable(value = "customer-uuid")String customerUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerService.deleteCustomer(customerUUID));
    }
}
