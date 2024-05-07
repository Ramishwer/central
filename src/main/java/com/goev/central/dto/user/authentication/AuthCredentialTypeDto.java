package com.goev.central.dto.user.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthCredentialTypeDto {
    private String name;
    private String uuid;
}

