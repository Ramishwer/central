package com.goev.central.dto.partner.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.partner.PartnerViewDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerActionDto {
    private String actionDetails;
    private String action;
    private PartnerViewDto partner;
    private String status;
}
