package com.goev.central.dto.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthUserDto {
    private String phoneNumber;
    private String email;
    private String uuid;
    private String organizationUUID;
    private String clientUUID;
}
