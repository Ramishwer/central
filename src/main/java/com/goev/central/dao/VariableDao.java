package com.goev.central.dao;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VariableDao extends BaseDao {
    private String key;
    private String value;
    private String type;
}
