package com.goev.central.dao.user.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDetailDao extends BaseDao {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;
    private String profileUrl;
    private Integer userId;
    private String remark;
    private DateTime deboardingDate;
    private DateTime onboardingDate;
    private DateTime suspensionDate;
}
