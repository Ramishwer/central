package com.goev.central.dto.payment;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dto.booking.BookingViewDto;
import lombok.*;
import org.joda.time.DateTime;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDto {
    private String uuid;
    private String status;
    private Integer amount;
    private Integer paidAmount;
    private Integer pendingAmount;
    private String gatewayUniqueIdentifier;
    private String gatewayTransactionIdentifier;
    private PaymentGatewayDto paymentGateway;
    private String entityUUID;
    private Map<String,String> entityDetails;
    private String paymentMode;
    private String paymentMethod;
    private String paymentDescription;
    private String paymentURL;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime transactionTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime expiryTime;
    private String paymentType;
    private String transactionPurpose;
    private String payload;
    private String signature;
    private String verificationMethod;
}



