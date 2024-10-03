package com.goev.central.dto.vehicle.detail;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dto.common.FileDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;
import com.goev.central.dto.vehicle.VehicleStatsDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.transfer.VehicleTransferDto;
import lombok.*;
import org.joda.time.DateTime;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDto {
    private String imageUrl;
    private String uuid;
    private String plateNumber;
    private String status;
    private PartnerViewDto partnerDetails;
    private VehicleTransferDto vehicleTransferDetails;
    private String subStatus;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime computedAvailableTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime computedChargingTime;
    private String locationStatus;
    private LocationDto locationDetails;
    private VehicleViewDto vehicleDetails;
    private List<VehicleSegmentDto> segments;
    private VehicleStatsDto stats;

    public static VehicleDto fromDao(VehicleDao vehicle) {
        if(vehicle == null)
            return null;
        return VehicleDto.builder()
                .uuid(vehicle.getUuid())
                .status(vehicle.getStatus())
                .subStatus(vehicle.getSubStatus())
                .locationDetails(ApplicationConstants.GSON.fromJson(vehicle.getLocationDetails(), LocationDto.class))
                .partnerDetails(ApplicationConstants.GSON.fromJson(vehicle.getPartnerDetails(), PartnerViewDto.class))
                .vehicleTransferDetails(ApplicationConstants.GSON.fromJson(vehicle.getVehicleTransferDetails(), VehicleTransferDto.class))
                .vehicleDetails(VehicleViewDto.fromDao(vehicle))
                .build();
    }
}
