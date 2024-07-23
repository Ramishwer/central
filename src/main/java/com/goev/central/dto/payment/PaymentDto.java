package com.goev.central.dto.payment;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.booking.BookingViewDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDto {
    private String uuid;
    private String payingEntityType;
    private String payingEntityUUID;
    private String payingFor;
    private String paymentUUID;
    private String type;
    private String paymentMethod;
    private String paymentMode;
    private String status;
    private BookingViewDto booking;
}



