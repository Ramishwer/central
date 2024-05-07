package com.goev.central.dto.payout;

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
public class PayoutModelConfigurationDto {
    private PayoutElementDto payoutElement;
    private PayoutModelDto payoutModel;
    private String day;
    private Map<String,String> variableValues;
    private String status;
    private String uuid;
}
