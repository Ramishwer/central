package com.goev.central.dto.challan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChallanDto {
    private String uuid;
    private PartnerViewDto partnerDetails;
    private VehicleViewDto vehicleDetails;
    private String challanType;
    private String remark;
    private String challanUrl;
    private String status;
}
