package com.goev.central.dto.vehicle;


import com.goev.central.dto.common.AttributeDto;
import lombok.*;

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
    private String manufacturer;
    private String model;
    private String year;
    private String uuid;
    private String state;
    private List<AttributeDto> attributes;
}
