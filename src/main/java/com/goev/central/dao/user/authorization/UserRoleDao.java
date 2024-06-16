package com.goev.central.dao.user.authorization;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRoleDao extends BaseDao {
    private String name;
    private String description;
}
