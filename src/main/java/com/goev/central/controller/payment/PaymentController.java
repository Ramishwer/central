package com.goev.central.controller.payment;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payment.PaymentDto;
import com.goev.central.service.payment.PaymentService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/payment-management")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payments")
    public ResponseDto<PaginatedResponseDto<PaymentDto>> getPayments(@RequestParam("count") Integer count,
                                                                     @RequestParam("start") Integer start,
                                                                     @RequestParam("from") Long from,
                                                                     @RequestParam("to") Long to,
                                                                     @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, paymentService.getPayments());
    }


    @GetMapping("/payments/{payment-uuid}")
    public ResponseDto<PaymentDto> getPaymentDetails(@PathVariable(value = "payment-uuid") String paymentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, paymentService.getPaymentDetails(paymentUUID));
    }


}
