package com.goev.central.dto.vehicle.detail;


import com.goev.central.dto.common.AttributeDto;
import lombok.*;
import org.joda.time.DateTime;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class VehicleDto {
    private String plateNumber;
    private String vinNumber;
    private String motorNumber;
    private DateTime registrationDate;
    private String uuid;
    private String state;
    private List<VehicleAttributeDto> attributes;
}
