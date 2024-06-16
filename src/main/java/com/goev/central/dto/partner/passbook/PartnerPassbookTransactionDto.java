package com.goev.central.dto.partner.passbook;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.partner.payout.PartnerPayoutDto;
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
