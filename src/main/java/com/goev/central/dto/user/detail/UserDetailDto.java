package com.goev.central.dto.user.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.user.UserViewDto;
import com.goev.central.dto.user.authorization.UserRoleDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailDto {
    private UserViewDto userDetails;
    private String uuid;
    private String firstName;
    private String lastName;
    private String state;
    private String profileUrl;
    private UserRoleDto role;
}
