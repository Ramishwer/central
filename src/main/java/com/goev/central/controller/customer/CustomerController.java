//package com.goev.central.controller;
//
//import com.goev.central.dto.common.PaginatedResponseDto;
//import com.goev.central.dto.customer.detail.CustomerDetailsDto;
//import com.goev.central.dto.customer.detail.CustomerDto;
//import com.goev.central.service.customer.CustomerService;
//import com.goev.lib.dto.ResponseDto;
//import com.goev.lib.dto.StatusDto;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping(value = "/api/v1/customer-management")

//import lombok.AllArgsConstructor;
//
//@AllArgsConstructor
//public class CustomerController {
//
//
//    private final CustomerService customerService;
//
//
//    @GetMapping("/customers")
//    public ResponseDto<PaginatedResponseDto<CustomerDto>> getCustomers(){
//        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerService.getCustomers());
//    }
//    @PostMapping("/customers")
//    public ResponseDto<CustomerDetailsDto> createCustomer(@RequestBody CustomerDetailsDto customerDto){
//        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerService.createCustomer(customerDto));
//    }
//
//    @PutMapping("/customers/{customer-uuid}")
//    public ResponseDto<CustomerDetailsDto> updateCustomer(@PathVariable(value = "customer-uuid")String customerUUID, @RequestBody CustomerDetailsDto customerDto){
//        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerService.updateCustomer(customerUUID,customerDto));
//    }
//
//    @GetMapping("/customers/{customer-uuid}")
//    public ResponseDto<CustomerDetailsDto> getCustomerDetails(@PathVariable(value = "customer-uuid")String customerUUID){
//        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerService.getCustomerDetails(customerUUID));
//    }
//
//    @DeleteMapping("/customers/{customer-uuid}")
//    public ResponseDto<Boolean> deleteCustomer(@PathVariable(value = "customer-uuid")String customerUUID){
//        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerService.deleteCustomer(customerUUID));
//    }
//}
