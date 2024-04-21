package com.goev.central.dto.partner.attendance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.partner.payout.PartnerPayoutModelDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerShiftConfigurationDto {
    private String uuid;
    private String shiftStart;
    private String shiftEnd;
    private PartnerPayoutModelDto payoutModel;
}
