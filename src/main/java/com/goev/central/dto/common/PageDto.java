package com.goev.central.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDto {
    private String lastElementUUID;
    private Integer start;
    private Integer limit;
}
