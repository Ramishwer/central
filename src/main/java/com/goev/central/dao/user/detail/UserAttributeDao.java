package com.goev.central.dao.user.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserAttributeDao extends BaseDao {
    private Integer userId;
    private String attributeKey;
    private String attributeValue;
}
