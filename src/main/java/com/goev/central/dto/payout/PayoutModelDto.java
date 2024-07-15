package com.goev.central.dto.payout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dao.payout.PayoutModelDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayoutModelDto {
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

    public static PayoutModelDto fromDao(PayoutModelDao payoutModelDao) {
        return PayoutModelDto.builder()
                .uuid(payoutModelDao.getUuid())
                .status(payoutModelDao.getStatus())
                .name(payoutModelDao.getName())
                .description(payoutModelDao.getDescription())
                .triggerType(payoutModelDao.getTriggerType())
                .build();
    }
}
