package com.goev.central.dto.incentive;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dao.incentive.IncentiveModelDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IncentiveModelDto {
    private String name;
    private String description;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime applicableFromTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime applicableToTime;
    private String triggerType;
    private String status;
    private String uuid;
    private String config;
    private String rules;

    public static IncentiveModelDto fromDao(IncentiveModelDao incentiveModelDao) {
        if(incentiveModelDao == null)
            return null;
        return IncentiveModelDto.builder()
                .uuid(incentiveModelDao.getUuid())
                .status(incentiveModelDao.getStatus())
                .name(incentiveModelDao.getName())
                .description(incentiveModelDao.getDescription())
                .triggerType(incentiveModelDao.getTriggerType())
                .build();
    }
}
