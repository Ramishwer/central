package com.goev.central.dto.booking;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.payment.PaymentDto;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingPaymentDto {
    private String uuid;
    private String paymentMode;
    private String paymentMethod;
    private String status;
    private Integer amount;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime transactionTime;
    private String transactionPurpose;
    private CustomerViewDto customerDetails;
    private BookingViewDto booking;
    private PaymentDto payment;
}



