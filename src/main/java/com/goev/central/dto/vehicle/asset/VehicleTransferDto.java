package com.goev.central.dto.vehicle.asset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.location.LocationDto;
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
public class VehicleTransferDto {
    private String uuid;
    private VehicleViewDto vehicle;
    private String transferType;
    private String transferFromUUID;
    private String transferToUUID;
    private String status;
    private String transferDetails;
    private LocationDto transferLocationDetails;
    private Integer odometerReading;
    private Integer socReading;
}
