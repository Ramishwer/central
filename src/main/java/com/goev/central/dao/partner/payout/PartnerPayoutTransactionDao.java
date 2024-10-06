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
    private String date;
    private String day;
    private Integer partnerPayoutId;
    private Integer partnerId;
    private Integer amount;
    private String title;
    private String subtitle;
    private String message;
    private DateTime transactionTime;
    private String calculatedPayoutElements;
    private String config;
    private String status;
}


