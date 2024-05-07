package com.goev.central.dto.system.instance;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemInstanceDto {
    private String uuid;
    private String name;
    private String hostname;
}
