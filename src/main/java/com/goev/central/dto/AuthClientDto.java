package com.goev.central.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthClientDto {
    private String clientKey;
    private String uuid;
    private Boolean isUserRegistrationAllowed;
    private String organizationUUID;
}
