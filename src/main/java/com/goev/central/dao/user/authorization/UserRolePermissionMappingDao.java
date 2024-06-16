package com.goev.central.dao.user.authorization;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRolePermissionMappingDao extends BaseDao {
    private Integer userId;
    private Integer roleId;
    private Integer userPermissionId;
}
