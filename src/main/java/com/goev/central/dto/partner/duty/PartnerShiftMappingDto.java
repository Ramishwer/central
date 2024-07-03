package com.goev.central.dto.partner.duty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.duty.PartnerShiftMappingDao;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.shift.ShiftConfigurationDto;
import com.goev.central.dto.shift.ShiftDto;
import com.google.gson.reflect.TypeToken;
import lombok.*;

import java.lang.reflect.Type;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerShiftMappingDto {
    private String uuid;
    private ShiftDto shift;
    private PartnerViewDto partner;
    private Map<String, ShiftConfigurationDto> shiftConfig;
    private Map<String, Map<String, LocationDto>> locationConfig;
    private String dutyConfig;


    public static PartnerShiftMappingDto fromDao(PartnerShiftMappingDao partnerShiftMappingDao, PartnerViewDto partner, ShiftDto shift) {
        Type locationMapType = new TypeToken<Map<String, Map<String, LocationDto>>>() {
        }.getType();
        Type shiftMapType = new TypeToken<Map<String, ShiftConfigurationDto>>() {
        }.getType();


        return PartnerShiftMappingDto.builder()
                .partner(partner)
                .shift(shift)
                .dutyConfig(partnerShiftMappingDao.getDutyConfig())
                .shiftConfig(ApplicationConstants.GSON.fromJson(partnerShiftMappingDao.getShiftConfig(), shiftMapType))
                .locationConfig(ApplicationConstants.GSON.fromJson(partnerShiftMappingDao.getLocationConfig(), locationMapType))
                .uuid(partnerShiftMappingDao.getUuid())
                .build();


    }

}
