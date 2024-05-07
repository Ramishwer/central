package com.goev.central.dto.challan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.customer.detail.CustomerDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.VehicleDto;
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
    private PartnerViewDto partner;
    private VehicleViewDto vehicle;
    private String challanType;
    private String remark;
    private String challanUrl;
    private String status;
}
