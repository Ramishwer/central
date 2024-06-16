package com.goev.central.dao.user.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserActionDao extends BaseDao {
    private String actionDetails;
    private String action;
    private Integer partnerId;
    private String status;
    private Integer userSessionId;
}
