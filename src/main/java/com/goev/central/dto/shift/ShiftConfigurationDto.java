package com.goev.central.dto.shift;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.payout.PayoutModelDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShiftConfigurationDto {
    private String uuid;
    private ShiftDto shift;
    private String estimatedIn ;
    private String estimatedOut;
    private String day;
    private String minimumIn ;
    private String maximumIn ;
    private String minimumOut;
    private String maximumOut;
    private String autoOut ;
    private PayoutModelDto payoutModel ;
    private String startRules ;
    private String endRules ;
}



