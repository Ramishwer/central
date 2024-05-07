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
public class PartnerPayoutTransactionDao extends BaseDao {
    private Integer payoutModelId;
    private String date;
    private String day;
    private Integer partnerPayoutId;
    private Integer partnerId;
    private String amount;
    private String title;
    private String subtitle;
    private String message;
    private DateTime transactionTime;
    private String transactionType;
    private String calculatedPayoutElements;
    private String status;
}


