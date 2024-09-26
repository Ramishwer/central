package com.goev.central.dto.payment;

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
public class PaymentGatewayDto {
    private String uuid;
    private String name;
    private String description;
    private Map<String,String> configuration;
}
