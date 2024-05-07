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
    private String phoneNumber;
    private String role;
    private String authUUID;
    private String uuid;
    private String profileUrl;
}
