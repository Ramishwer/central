package com.goev.central.dao.user.authorization;

import com.goev.central.dto.user.detail.UserDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

import static com.goev.central.constant.ApplicationConstants.GSON;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserPermissionDao extends BaseDao {
    private String name;
    private String description;
    private String type;
    private String permissionProperties;
}
