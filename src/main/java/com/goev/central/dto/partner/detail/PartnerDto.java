package com.goev.central.dto.partner.detail;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerDto {
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String uuid;
    private String punchId;
    private String state;
    private String fathersName;
    private String aadhaarCardNumber;
    private String localAddress;
    private String permanentAddress;
    private String profileUrl;
    private String authUUID;
    private List<PartnerAttributeDto> attributes;

}
