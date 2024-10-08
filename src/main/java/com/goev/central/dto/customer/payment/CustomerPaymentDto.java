package com.goev.central.dto.customer.payment;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dto.booking.BookingViewDto;
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
public class CustomerPaymentDto {
    private String uuid;
    private String paymentMode;
    private String paymentMethod;
    private String status;
    private Integer amount;
    private Integer pendingAmount;
    private Integer paidAmount;
    private String transactionPurpose;
    private String gatewayUniqueIdentifier;
    private String gatewayTransactionIdentifier;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime transactionTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime expiryTime;
    private String paymentType;
    private String paymentUUID;
    private CustomerViewDto customer;
    private BookingViewDto booking;
    private PaymentDto payment;
}



