package com.goev.central.dto.partner;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.detail.PartnerDao;
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
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime deboardingDate;
    private String uuid;
    private String state;
    private String profileUrl;
    private LocationDto homeLocation;
    private PartnerStatsDto stats;


    public static String getPartnerName(PartnerViewDto partnerViewDto) {
        if (partnerViewDto == null || partnerViewDto.getFirstName() == null)
            return "";
        if (partnerViewDto.getLastName() == null)
            return partnerViewDto.getFirstName();
        return partnerViewDto.getFirstName() + " " + partnerViewDto.getLastName();
    }

    public static PartnerViewDto fromDao(PartnerDao partnerDao) {
        if (partnerDao.getViewInfo() == null)
            return null;
        PartnerViewDto result =ApplicationConstants.GSON.fromJson(partnerDao.getViewInfo(), PartnerViewDto.class);
        result.setUuid(partnerDao.getUuid());
        return result;
    }
}
