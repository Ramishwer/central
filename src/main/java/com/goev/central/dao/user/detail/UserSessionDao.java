package com.goev.central.dao.user.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserSessionDao extends BaseDao {
    private Integer userId;
    private DateTime lastActiveTime;
    private String status;
    private String authSessionUuid;
}
