package com.goev.central.dto.partner.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerAppPropertyDto {
    private String uuid;
    private String propertyName;
    private String propertyDescription;
    private String propertyType;
    private String propertyValue;
}
