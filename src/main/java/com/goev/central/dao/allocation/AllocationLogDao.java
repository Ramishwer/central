package com.goev.central.dao.allocation;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AllocationLogDao extends BaseDao {
    private String allocationAttemptUUID;
    private Integer allocationResultId;
    private String log;
}