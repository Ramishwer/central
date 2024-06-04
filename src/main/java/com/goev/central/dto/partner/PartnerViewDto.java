package com.goev.central.dto.partner;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dto.location.LocationDto;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerViewDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String punchId;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime onboardingDate;
    private String uuid;
    private String state;
    private String profileUrl;
    private LocationDto homeLocation;


    public static String getPartnerName(PartnerViewDto partnerViewDto) {
        if (partnerViewDto == null || partnerViewDto.getFirstName() == null)
            return "";
        if (partnerViewDto.getLastName() == null)
            return partnerViewDto.getFirstName();
        return partnerViewDto.getFirstName() + " " + partnerViewDto.getLastName();
    }
}
