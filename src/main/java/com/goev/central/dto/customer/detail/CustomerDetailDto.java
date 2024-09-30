package com.goev.central.dto.customer.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.customer.CustomerViewDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDetailDto {
    private CustomerViewDto customerDetails;
    private String uuid;
    private String phoneNumber;
    private String profileUrl;
    private String firstName;
    private String lastName;
    private String email;
    private String preferredLanguage;
}
