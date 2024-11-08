package com.goev.central.dto.earning;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dto.shift.ShiftDto;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EarningRuleDto {
    private String uuid;
    private String ruleid;
    private String city;
    private String businessType;
    private String clientName;
    private Integer fixedIncome;
    private Integer variableIncome;
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

    public static EarningRuleDto fromDao(EarningRuleDao earningRuleDao){
        if(earningRuleDao==null){
            return null;
        }
        return EarningRuleDto.builder()
                .uuid(earningRuleDao.getUuid())
                .ruleid(earningRuleDao.getRuleId())
                .city(earningRuleDao.getCity())
                .businessType(earningRuleDao.getBusinessType())
                .clientName(earningRuleDao.getClientName())
                .fixedIncome(earningRuleDao.getFixedIncome())
                .variableIncome(earningRuleDao.getVariableIncome())
                .checks(earningRuleDao.getChecks())
                .checkValue(earningRuleDao.getCheckValue())
                .validTill(earningRuleDao.getValidTill())
                .status(earningRuleDao.getIsActive()?"ACTIVE":"INACTIVE")
                .createdOn(earningRuleDao.getCreatedOn())
                .createdBy(earningRuleDao.getCreatedBy())
                .build();
    }


}
