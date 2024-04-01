package com.goev.central.dto.partner.detail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerAccountDto {
    private String accountNumber;
    private String accountHolderName;
    private String ifscCode;
    private String uuid;
}
