package com.goev.central.dto.partner.passbook;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerPassbookDetailDto {

    private String uuid;
    private Integer currentBalance;

}
