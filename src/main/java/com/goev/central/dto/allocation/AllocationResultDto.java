package com.goev.central.dto.allocation;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    private DateTime allocationStartTime;

    private DateTime allocationEndTime;
}
