package com.goev.central.dto.allocation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllocationLogDto {
    private String uuid;
    private String allocationAttemptUUID;
    private AllocationResultDto allocationResultDto;
    private String log;
}
