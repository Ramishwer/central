package com.goev.central.dto.payment;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDetailDto {
    private String uuid;
    private PaymentDto payment;
    private String type;
    private String paymentMethod;
    private String paymentMode;
    private String status;
    private String request;
    private String response;
}



