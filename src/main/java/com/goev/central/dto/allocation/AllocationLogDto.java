package com.goev.central.dto.allocation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.allocation.AllocationLogDao;
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


    public static AllocationLogDto fromDao(AllocationLogDao allocationLogDao) {
        return AllocationLogDto.builder()
                .uuid(allocationLogDao.getUuid())
                .build();
    }
}
