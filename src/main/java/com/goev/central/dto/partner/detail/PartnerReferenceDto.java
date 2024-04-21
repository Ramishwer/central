package com.goev.central.dto.partner.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerReferenceDto {
    private String name;
    private String phoneNumber;
    private String relation;
    private String email;
    private String address;
    private String uuid;
}
