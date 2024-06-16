package com.goev.central.dto.customer;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerViewDto {

    private String uuid;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String profileUrl;
    private String status;
    private String email;
    private String preferredLanguage;
}
