package com.goev.central.dto.partner.duty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.shift.ShiftConfigurationDto;
import com.goev.central.dto.vehicle.detail.VehicleCategoryDto;
import com.google.common.reflect.TypeToken;
import lombok.*;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDutyDto {
    private String uuid;
    private PartnerViewDto partnerDetails;
    private PartnerShiftDto shiftDetails;
    private Long plannedTotalOnlineTimeInMillis;
    private Long plannedTotalPauseTimeInMillis;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime plannedDutyStartTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime plannedDutyEndTime;
    private LocationDto plannedDutyStartLocationDetails;
    private LocationDto plannedDutyEndLocationDetails;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime actualDutyStartTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime actualDutyEndTime;
    private LocationDto actualDutyStartLocationDetails;
    private LocationDto actualDutyEndLocationDetails;
    private Long actualTotalOnlineTimeInMillis;
    private Long actualTotalPauseTimeInMillis;
    private String status;
    private Set<PartnerDutyVehicleDetailsDto> vehicles;

    public static PartnerDutyDto fromDao(PartnerDutyDao dutyDao, PartnerViewDto partner, PartnerShiftDao shift) {

        if(dutyDao==null)
            return null;

        PartnerDutyDto result = PartnerDutyDto.builder()
                .uuid(dutyDao.getUuid())
                .status(dutyDao.getStatus())
                .partnerDetails(partner)
                .plannedTotalOnlineTimeInMillis(dutyDao.getPlannedTotalOnlineTimeInMillis())
                .plannedTotalPauseTimeInMillis(dutyDao.getPlannedTotalPauseTimeInMillis())
                .plannedDutyStartTime(dutyDao.getPlannedDutyStartTime())
                .plannedDutyEndTime(dutyDao.getPlannedDutyEndTime())
                .plannedDutyStartLocationDetails(ApplicationConstants.GSON.fromJson(dutyDao.getPlannedDutyStartLocationDetails(), LocationDto.class))
                .actualTotalOnlineTimeInMillis(dutyDao.getActualTotalOnlineTimeInMillis())
                .actualTotalPauseTimeInMillis(dutyDao.getActualTotalPauseTimeInMillis())
                .actualDutyStartTime(dutyDao.getActualDutyStartTime())
                .actualDutyEndTime(dutyDao.getActualDutyEndTime())
                .actualDutyStartLocationDetails(ApplicationConstants.GSON.fromJson(dutyDao.getActualDutyStartLocationDetails(), LocationDto.class))
                .actualDutyEndLocationDetails(ApplicationConstants.GSON.fromJson(dutyDao.getActualDutyEndLocationDetails(), LocationDto.class))
                .build();

        if (shift != null) {
            PartnerShiftDto shiftDto = PartnerShiftDto.fromDao(shift,partner);
            result.setShiftDetails(shiftDto);
        }

        if (dutyDao.getVehicles() != null) {
            Type t= new TypeToken<Set<PartnerDutyVehicleDetailsDto>>(){}.getRawType();
            Set<PartnerDutyVehicleDetailsDto> vehicles = ApplicationConstants.GSON.fromJson(dutyDao.getVehicles(),t);
            result.setVehicles(vehicles);
        }

        return result;

    }

}
