package com.goev.central.dao.partner.passbook;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerPassbookTransactionDao extends BaseDao {
    private Integer partnerId;
    private Integer amount;
    private String transactionType;
    private String remark;
    private String verificationCode;
    private DateTime transactionTime;
    private String status;
    private Integer openingBalance;
    private Integer closingBalance;
    private Integer partnerPayoutId;
    private String entryType;
}


