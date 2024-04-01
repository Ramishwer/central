package com.goev.central.dto.partner.detail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerReferenceDto {
    private String name;
    private String phoneNumber;
    private String relation;
    private String email;
    private String address;
    private String uuid;
}
