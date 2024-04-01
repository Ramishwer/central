package com.goev.central.dto.user.detail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDetailsDto {
    private UserDto details;
    private String uuid;
}
