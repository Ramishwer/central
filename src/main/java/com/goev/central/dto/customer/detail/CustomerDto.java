package com.goev.central.dto.customer.detail;


import com.goev.central.dto.common.AttributeDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerDto {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String uuid;
    private String state;
    private List<AttributeDto> attributes;
}
