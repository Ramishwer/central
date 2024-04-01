package com.goev.central.dao.user.detail;

import com.goev.central.dto.user.detail.UserDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

import static com.goev.central.constant.ApplicationConstants.GSON;

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
