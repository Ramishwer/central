package com.goev.central.dto.partner.payout;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.dto.payout.PayoutModelDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDutyPayoutDetailDto {

    private String uuid;
    private PartnerDutyDto partnerDuty;
    private Integer plannedAmount;
    private Integer actualAmount;
    private Integer plannedDeductionAmount;
    private Integer actualDeductionAmount;
    private String actionDetails;
    private Integer creditAmount;
    private Integer debitAmount;
    private PayoutModelDto payoutModel;

}
