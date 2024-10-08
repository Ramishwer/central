package com.goev.central.dto.customer.wallet;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.payment.PaymentDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerWalletTransactionDto {

    private String uuid;
    private Integer openingWalletBalance;
    private Integer closingWalletBalance;
    private BookingDto booking;
    private PaymentDto payment;
    private String message;
    private String subtitle;
    private String remark;
    private String status;
    private String transactionType;
    private String entryType;
    private Integer amount;

}
