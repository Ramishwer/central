package com.goev.central.service.payment;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payment.PaymentDetailDto;

public interface PaymentDetailService {
    PaginatedResponseDto<PaymentDetailDto> getPaymentDetails(String paymentUUID);

    PaymentDetailDto getPaymentDetailDetails(String paymentUUID, String paymentDetailUUID);
}
