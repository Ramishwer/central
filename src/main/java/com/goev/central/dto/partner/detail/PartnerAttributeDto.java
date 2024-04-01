package com.goev.central.dto.partner.detail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerAttributeDto {
    private String uuid;
    private String attributeKey;
    private String attributeValue;
    private String name;
}
