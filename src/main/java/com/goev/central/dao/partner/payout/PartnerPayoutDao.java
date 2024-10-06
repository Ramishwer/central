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
    private Integer totalWorkingDays;
    private String payoutSummary;
    private Integer payoutTotalAmount;
    private String payoutConfig;
}


