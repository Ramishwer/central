package com.goev.central.dto.engine;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EngineRuleDto {
    private String uuid;
    private String ruleName;
    private String whenCondition;
    private String thenAction;
    private Integer priority;
    private String ruleType;
    private Integer weightage;
}
