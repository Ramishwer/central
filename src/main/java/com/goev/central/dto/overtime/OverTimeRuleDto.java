package com.goev.central.dto.overtime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dao.overtime.OverTimeRuleDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OverTimeRuleDto {
    private String uuid;
    private String ruleid;
    private String city;
    private String businessType;
    private String clientName;
    private Integer overTimeAmount;
    private String checks;
    private Integer checkValue;
    private Long validTill;
    private String status;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime createdOn;
    private String createdBy;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime updatedOn;
    private String updatedBy;
    private Boolean isActive;



    public static OverTimeRuleDto fromDao(OverTimeRuleDao overTimeRuleDao){
        if(overTimeRuleDao==null){
            return null;
        }
        return OverTimeRuleDto.builder()
                .uuid(overTimeRuleDao.getUuid())
                .ruleid(overTimeRuleDao.getRuleId())
                .city(overTimeRuleDao.getCity())
                .businessType(overTimeRuleDao.getBusinessType())
                .clientName(overTimeRuleDao.getClientName())
                .overTimeAmount(overTimeRuleDao.getOvertimeAmount())
                .checks(overTimeRuleDao.getChecks())
                .checkValue(overTimeRuleDao.getCheckValue())
                .validTill(overTimeRuleDao.getValidTill())
                .status(overTimeRuleDao.getIsActive()?"ACTIVE":"INACTIVE")
                .createdOn(overTimeRuleDao.getCreatedOn())
                .createdBy(overTimeRuleDao.getCreatedBy())
                .isActive(overTimeRuleDao.getIsActive())
                .build();
    }
}
