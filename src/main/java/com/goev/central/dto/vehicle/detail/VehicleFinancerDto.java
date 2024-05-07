package com.goev.central.dto.vehicle.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleFinancerDto {
    private String name;
    private String description;
    private String uuid;
}
