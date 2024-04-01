package com.goev.central.dao.partner.payout;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerBookingPayoutDetailDao extends BaseDao {
    private Integer partnerId;
    private Integer partnerPayoutId;
    private Integer bookingId;
    private Integer plannedAmount;
    private Integer actualAmount;
    private Integer plannedDeductionAmount;
    private Integer actualDeductionAmount;
    private String actionDetails;
    private Integer creditAmount;
    private Integer debitAmount;
    private Integer payoutModelId;
}


