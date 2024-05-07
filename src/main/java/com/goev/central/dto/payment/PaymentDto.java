package com.goev.central.dto.payment;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.booking.BookingViewDto;
import lombok.*;
import org.joda.time.DateTime;

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
    private String status;
    private BookingViewDto booking;
}



