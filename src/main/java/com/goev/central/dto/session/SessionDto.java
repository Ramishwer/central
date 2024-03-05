package com.goev.central.dto.session;


import com.goev.central.dto.common.AttributeDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SessionDto {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String uuid;
    private String authId;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private List<AttributeDto> attributes;
    private String state;
}
