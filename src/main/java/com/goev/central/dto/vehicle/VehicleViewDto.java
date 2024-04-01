package com.goev.central.dto.vehicle;

import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class VehicleViewDto {
    private String plateNumber;
    private String vinNumber;
    private String motorNumber;
    private DateTime registrationDate;
    private String uuid;
    private String state;
}
