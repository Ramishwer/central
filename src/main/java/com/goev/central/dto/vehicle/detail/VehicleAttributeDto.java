package com.goev.central.dto.vehicle.detail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class VehicleAttributeDto {
    private String uuid;
    private String attributeKey;
    private String attributeValue;
    private String name;
}
