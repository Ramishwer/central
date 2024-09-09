package com.goev.central.dto.customer;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerViewDto {

    private String uuid;
    private String state;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String profileUrl;
    private String status;
    private String email;
    private String preferredLanguage;
    private String remark;
    private Map<String,Object> fields;
}
