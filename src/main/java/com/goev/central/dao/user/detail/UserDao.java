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
public class UserDao extends BaseDao {
    private Integer userDetailsId;
    private String email;
    private String phoneNumber;
    private String role;
    private String authId;
}
