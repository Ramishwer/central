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
public class EngineRuleFieldDto {
    private String uuid;
    private String ruleType;
    private String fieldName;
    private Integer parentId;
    private String fieldType;
    private String packageName;
    private String valueType;
    private Boolean isSortable;
    private Integer weightage;
}
