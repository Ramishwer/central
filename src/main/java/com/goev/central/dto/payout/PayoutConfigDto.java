package com.goev.central.dto.payout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.VariableDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayoutConfigDto {
    private PayoutModelDto payoutModelConfig;
    private List<PayoutElementDto> payoutElementsConfig;
}
