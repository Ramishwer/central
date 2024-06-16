package com.goev.central.service.payment;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payment.PaymentDto;

public interface PaymentService {
    PaginatedResponseDto<PaymentDto> getPayments();

    PaymentDto getPaymentDetails(String paymentUUID);

}
