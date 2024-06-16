package com.goev.central.dto.partner.duty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDutyDto {
    private String uuid;
    private PartnerViewDto partner;
    private PartnerShiftDto partnerShift;
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
}
