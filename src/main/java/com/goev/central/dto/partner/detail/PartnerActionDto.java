package com.goev.central.dto.partner.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.enums.partner.PartnerAction;
import com.goev.lib.dto.LatLongDto;
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
    private PartnerAction action;
    private PartnerViewDto partner;
    private String status;
    private LatLongDto location;
    private String qrString;
    private String vehicleUUID;
}
