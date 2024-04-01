
package com.goev.central.dao.allocation;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AllocationResultDao extends BaseDao {
    private String allocationType;
    private String allocationAttemptUUID;
    private String allocationResult;
    private String allocationTrigger;
    private DateTime allocationStartTime;
    private DateTime allocationEndTime;
}



