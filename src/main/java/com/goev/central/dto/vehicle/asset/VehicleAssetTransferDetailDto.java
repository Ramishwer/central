package com.goev.central.dto.vehicle.asset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.asset.AssetDto;
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
public class VehicleAssetTransferDetailDto {
    private String uuid;
    private AssetDto asset;
    private String status;
}
