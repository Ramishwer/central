package com.goev.central.dto.partner.payout;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.dto.partner.payout.PartnerPayoutDto;
import com.goev.central.dto.payout.PayoutModelDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerBookingPayoutDetailDto {

    private String uuid;
    private BookingViewDto booking;
    private Integer plannedAmount;
    private Integer actualAmount;
    private Integer plannedDeductionAmount;
    private Integer actualDeductionAmount;
    private String actionDetails;
    private Integer creditAmount;
    private Integer debitAmount;
    private PayoutModelDto payoutModel;

}
