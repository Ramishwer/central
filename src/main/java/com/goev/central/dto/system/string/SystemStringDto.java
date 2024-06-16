package com.goev.central.dto.system.string;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemStringDto {
    private String uuid;
    private String language;
    private List<Map<String, Object>> strings;
}
