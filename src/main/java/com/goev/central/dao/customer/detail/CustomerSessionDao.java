package com.goev.central.dao.customer.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerSessionDao extends BaseDao {
    private Integer customerId;
    private DateTime lastActiveTime;
    private String status;
    private String authSessionUuid;
}


