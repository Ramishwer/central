package com.goev.central.dto.partner.duty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.payout.PayoutModelDto;
import com.goev.central.dto.shift.ShiftConfigurationDto;
import com.goev.central.dto.shift.ShiftDto;
import com.goev.central.dto.vehicle.detail.VehicleCategoryDto;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerShiftDto {
    private String uuid;
    private String shiftStart;
    private String shiftEnd;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime applicableFromTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime applicableToTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime estimatedStartTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime estimatedEndTime;
    private PartnerViewDto partnerDetails;
    private String day;
    private ShiftDto shift;
    private ShiftConfigurationDto shiftConfig;
    private LocationDto inLocationDetails;
    private LocationDto outLocationDetails;
    private LocationDto onlineLocationDetails;
    private LocationDto offlineLocationDetails;
    private VehicleCategoryDto assignableVehicleCategoryDetails;
    private String type;
    private String status;
    private String subStatus;
    private String dutyConfig;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime dutyDate;



    public static PartnerShiftDto fromDao(PartnerShiftDao shift,PartnerViewDto partner){
        if(shift == null)
            return null;
        return PartnerShiftDto.builder()
                .shiftStart(shift.getShiftStart())
                .shiftEnd(shift.getShiftEnd())
                .estimatedStartTime(shift.getEstimatedStartTime())
                .estimatedEndTime(shift.getEstimatedEndTime())
                .shiftConfig(ApplicationConstants.GSON.fromJson(shift.getShiftConfig(), ShiftConfigurationDto.class))
                .type(shift.getType())
                .inLocationDetails(ApplicationConstants.GSON.fromJson(shift.getInLocationDetails(), LocationDto.class))
                .outLocationDetails(ApplicationConstants.GSON.fromJson(shift.getOutLocationDetails(), LocationDto.class))
                .onlineLocationDetails(ApplicationConstants.GSON.fromJson(shift.getOnlineLocationDetails(), LocationDto.class))
                .offlineLocationDetails(ApplicationConstants.GSON.fromJson(shift.getOfflineLocationDetails(), LocationDto.class))
                .assignableVehicleCategoryDetails(ApplicationConstants.GSON.fromJson(shift.getAssignableVehicleCategoryDetails(), VehicleCategoryDto.class))
                .dutyDate(shift.getDutyDate())
                .partnerDetails(partner)
                .status(shift.getStatus())
                .subStatus(shift.getSubStatus())
                .build();
    }
}
