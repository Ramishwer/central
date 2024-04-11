package com.goev.central.dto.vehicle.asset;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class VehicleAssetDto {
    private String uuid;
    private String attributeKey;
    private String attributeValue;
    private String name;
}
