package com.goev.central.dao.payout;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PayoutModelDao extends BaseDao {
    private String name;
    private String description;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
    private String triggerType;
    private String status;
}


