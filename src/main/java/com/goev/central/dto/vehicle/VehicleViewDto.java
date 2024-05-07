package com.goev.central.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.vehicle.detail.VehicleManufacturerDto;
import com.goev.central.dto.vehicle.detail.VehicleModelDto;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleViewDto {
    private String plateNumber;
    private String vinNumber;
    private String motorNumber;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime registrationDate;
    private String uuid;
    private String state;
    private LocationDto homeLocation;
    private VehicleModelDto vehicleModel;
    private String imageUrl;

}
