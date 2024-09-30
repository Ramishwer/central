package com.goev.central.dto.shift;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dao.shift.ShiftDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShiftDto {
    private String uuid;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime applicableFromTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime applicableToTime;
    private String name;
    private String description;
    private String shiftType;

    public static ShiftDto fromDao(ShiftDao shiftDao) {
        if(shiftDao == null)
            return null;
        return ShiftDto.builder()
                .uuid(shiftDao.getUuid())
                .name(shiftDao.getName())
                .description(shiftDao.getDescription())
                .shiftType(shiftDao.getShiftType())
                .build();
    }
}



