package com.goev.central.dao.partner.payout;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerPayoutDao extends BaseDao {
    private DateTime payoutStartDate;
    private DateTime payoutEndDate;
    private Integer partnerId;
    private String status;
    private DateTime finalizationDate;
    private String payoutSummary;
    private Integer payoutTotalBookingAmount;
    private Integer payoutTotalAmount;
    private Integer payoutTotalDeductionAmount;
    private Integer payoutTotalCreditAmount;
    private Integer payoutTotalDebitAmount;
}


