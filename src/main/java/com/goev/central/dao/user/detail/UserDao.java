package com.goev.central.dao.user.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDao extends BaseDao {
    private Integer userDetailsId;
    private String email;
    private String phoneNumber;
    private String role;
    private Integer roleId;
    private String authUuid;
    private String viewInfo;
    private String profileUrl;
    private String onboardingStatus;
    private String displayCode;
}
