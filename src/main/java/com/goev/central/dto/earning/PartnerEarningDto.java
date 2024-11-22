package com.goev.central.dto.earning;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dao.earning.PartnerEarningDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerEarningDto {
    private String uuid;
    private Integer partnerId;
    private String ruleId;
    private String businessType;
    private String clientName;
    private Float totalEarning;
    private Integer presentDays;
    private Integer absentDays;
    private Integer totalWorkingDays;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime startDate;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime endDate;
    private String status;


    public static PartnerEarningDto fromDao(PartnerEarningDao partnerEarningDao){
        if(partnerEarningDao==null){
            return null;
        }
        return PartnerEarningDto.builder()
                .uuid(partnerEarningDao.getUuid())
                .ruleId(partnerEarningDao.getEarningRuleId())
                .businessType(partnerEarningDao.getBusinessType())
                .clientName(partnerEarningDao.getClientName())
                .totalEarning(partnerEarningDao.getTotalEarning())
                .presentDays(partnerEarningDao.getPresentDays())
                .absentDays(partnerEarningDao.getAbsentDays())
                .totalWorkingDays(partnerEarningDao.getTotalWorkingDays())
                .startDate(partnerEarningDao.getStartDate())
                .endDate(partnerEarningDao.getEndDate())
                .build();
    }
}
