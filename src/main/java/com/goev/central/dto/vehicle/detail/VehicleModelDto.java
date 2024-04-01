package com.goev.central.dto.vehicle.detail;

import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class VehicleModelDto {
    private String variant;
    private String model;
    private String year;
    private String month;
    private String batteryCapacity;
    private Integer kmRange;
    private String uuid;
    private VehicleManufacturerDto manufacturer;
}
