package com.goev.central.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.enums.vehicle.VehicleAction;
import com.goev.lib.dto.LatLongDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleActionDto {
    private String actionDetails;
    private VehicleAction action;
    private VehicleViewDto vehicle;
    private String status;
    private LatLongDto location;
    private String qrString;
    private String partnerUUID;
    private String remark;
}
