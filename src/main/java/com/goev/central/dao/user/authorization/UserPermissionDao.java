package com.goev.central.dao.user.authorization;

import com.goev.lib.dao.BaseDao;
import lombok.*;

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
