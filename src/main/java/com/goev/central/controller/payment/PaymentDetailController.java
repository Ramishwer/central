package com.goev.central.controller.payment;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payment.PaymentDetailDto;
import com.goev.central.service.payment.PaymentDetailService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/payment-management")
@AllArgsConstructor
public class PaymentDetailController {

    private final PaymentDetailService paymentDetailService;

    @GetMapping("/payments/{payment-UUID}/details")
    public ResponseDto<PaginatedResponseDto<PaymentDetailDto>> getEngineRuleFields(@PathVariable(value = "payment-uuid") String paymentUUID,
                                                                                   @RequestParam("count") Integer count,
                                                                                   @RequestParam("start") Integer start,
                                                                                   @RequestParam("from") Long from,
                                                                                   @RequestParam("to") Long to,
                                                                                   @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, paymentDetailService.getPaymentDetails(paymentUUID));
    }


    @GetMapping("/payments/{payment-UUID}/details/{payment-detail-uuid}")
    public ResponseDto<PaymentDetailDto> getEngineRuleFieldDetails(@PathVariable(value = "payment-uuid") String paymentUUID, @PathVariable(value = "payment-detail-uuid") String paymentDetailUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, paymentDetailService.getPaymentDetailDetails(paymentUUID, paymentDetailUUID));
    }

}
