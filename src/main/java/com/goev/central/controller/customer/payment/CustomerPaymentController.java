package com.goev.central.controller.customer.payment;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.payment.CustomerPaymentDto;
//import com.goev.central.service.customer.payment.CustomerPaymentService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerPaymentController {

//    private final CustomerPaymentService customerPaymentService;

    @GetMapping("/payments")
    public ResponseDto<PaginatedResponseDto<CustomerPaymentDto>> getCustomerPayments(
                                                                                               @RequestParam(value = "count", required = false) Integer count,
                                                                                               @RequestParam(value = "start", required = false) Integer start,
                                                                                               @RequestParam(value = "from", required = false) Long from,
                                                                                               @RequestParam(value = "to", required = false) Long to,
                                                                                               @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, new PaginatedResponseDto<>());
    }


    @GetMapping("/customers/{customer-uuid}/payments/{payment-uuid}")
    public ResponseDto<CustomerPaymentDto> getCustomerPaymentDetails(@PathVariable(value = "customer-uuid") String customerUUID,
                                                                               @PathVariable(value = "payment-uuid") String paymentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, new CustomerPaymentDto());
    }

}
