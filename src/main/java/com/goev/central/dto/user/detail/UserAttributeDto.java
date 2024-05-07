package com.goev.central.dto.user.detail;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAttributeDto {
    private String uuid;
    private String name;
    private String attributeKey;
    private String attributeValue;
}
