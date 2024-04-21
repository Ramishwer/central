package com.goev.central.dto.user.detail;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.common.AttributeDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String uuid;
    private String state;
    private List<AttributeDto> attributes;
}
