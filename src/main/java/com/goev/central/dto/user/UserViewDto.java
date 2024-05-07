package com.goev.central.dto.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserViewDto {
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String role;
    private String authUUID;
    private String uuid;
    private String profileUrl;
}
