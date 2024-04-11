package com.goev.central.dto.user.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthCredentialTypeDto {
    private String name;
    private String uuid;
}

