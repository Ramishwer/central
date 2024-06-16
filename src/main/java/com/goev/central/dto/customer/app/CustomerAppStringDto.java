package com.goev.central.dto.customer.app;

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
public class CustomerAppStringDto {
    private String uuid;
    private String language;
    private List<Map<String, Object>> strings;
}
