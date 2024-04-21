package com.goev.central.dto.vehicle.asset;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String attributeKey;
    private String attributeValue;
    private String name;
}
