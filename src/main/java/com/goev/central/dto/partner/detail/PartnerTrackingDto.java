package com.goev.central.dto.partner.detail;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.lib.dto.LatLongDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerTrackingDto {
    private PartnerDto partnerDetails;
    private LatLongDto location;
}
