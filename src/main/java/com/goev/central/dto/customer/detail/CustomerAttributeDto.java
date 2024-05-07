package com.goev.central.dto.customer.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.customer.CustomerViewDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerAttributeDto {
    private String uuid;
    private String name;
    private String attributeKey;
    private String attributeValue;
}
