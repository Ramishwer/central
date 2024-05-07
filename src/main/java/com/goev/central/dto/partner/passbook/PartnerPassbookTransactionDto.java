package com.goev.central.dto.partner.passbook;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.customer.detail.CustomerDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.dto.partner.payout.PartnerPayoutDto;
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
public class PartnerPassbookTransactionDto {

    private String uuid;
    private Integer amount;
    private String transactionType;
    private String remark;
    private String verificationCode;
    private DateTime transactionTime;
    private String status;
    private Integer openingBalance;
    private Integer closingBalance;
    private PartnerPayoutDto partnerPayout;
    private String entryType;

}
