package com.goev.central.dto.user.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.user.UserViewDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailDto {
    private UserViewDto user;
    private String uuid;
    private String firstName;
    private String lastName;
    private String state;
    private String profileUrl;
    private String role;
}
