package com.goev.central.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.vehicle.detail.VehicleModelDto;
import lombok.*;
import org.joda.time.DateTime;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleViewDto {
    private String uuid;
    private String state;
    private String imageUrl;
    private String plateNumber;
    private String vinNumber;
    private String motorNumber;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime registrationDate;
    private VehicleModelDto vehicleModel;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime onboardingDate;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime deboardingDate;
    private LocationDto homeLocation;
    private VehicleStatsDto stats;
    private String remark;
    private Map<String,Object> fields;

    public static VehicleViewDto fromDao(VehicleDao vehicleDao) {
        if (vehicleDao.getViewInfo() == null)
            return null;
        VehicleViewDto result = ApplicationConstants.GSON.fromJson(vehicleDao.getViewInfo(), VehicleViewDto.class);
        result.setUuid(vehicleDao.getUuid());
        return result;
    }

}
