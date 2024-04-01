package com.goev.central.dao.engine;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EngineRuleFieldDao extends BaseDao {
    private String ruleType;
    private String fieldName;
    private Integer parentId;
    private String fieldType;
    private String packageName;
    private String valueType;
    private Boolean isSortable;
}




