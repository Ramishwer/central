package com.goev.central.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.enums.vehicle.VehicleAction;
import com.goev.lib.dto.LatLongDto;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleActionDto {
    private String actionDetails;
    private VehicleAction action;
    private VehicleViewDto vehicleDetails;
    private String status;
    private LatLongDto location;
    private String qrString;
    private String partnerUUID;
    private String remark;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime timestamp;

}
