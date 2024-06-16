package com.goev.central.dto.partner.detail;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDto {
    private String uuid;
    private String punchId;
    private String authUUID;
    private String phoneNumber;
    private String status;
    private String subStatus;
    private String profileUrl;
    private VehicleViewDto vehicleDetails;
    private BookingViewDto bookingDetails;
    private LocationDto locationDetails;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime computedAvailableTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime computedShiftEndTime;
    private PartnerDutyDto dutyDetails;
    private PartnerShiftDto shiftDetails;
    private String locationStatus;

}
