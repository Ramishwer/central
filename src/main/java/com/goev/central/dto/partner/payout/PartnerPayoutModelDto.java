package com.goev.central.dto.partner.payout;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerPayoutModelDto {
    private String name;
    private String description;
    private String uuid;
}
