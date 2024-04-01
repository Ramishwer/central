package com.goev.central.dto.customer.detail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerDetailsDto {
    private CustomerDto details;
    private String uuid;
}
