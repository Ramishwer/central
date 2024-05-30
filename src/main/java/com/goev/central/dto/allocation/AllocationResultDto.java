package com.goev.central.dto.allocation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dao.allocation.AllocationResultDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllocationResultDto {
    private String uuid;
    private String allocationType;
    private String allocationAttemptUUID;
    private String allocationResult;
    private String allocationTrigger;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime allocationStartTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime allocationEndTime;

    public static AllocationResultDto fromDao(AllocationResultDao allocationResultDao) {
        return AllocationResultDto.builder()
                .uuid(allocationResultDao.getUuid())
                .build();
    }


}
