package com.goev.central.dao.engine;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EngineRuleDao extends BaseDao {
    private String ruleName;
    private String whenCondition;
    private String thenAction;
    private Integer priority;
    private String ruleType;
    private Integer weightage;
}




