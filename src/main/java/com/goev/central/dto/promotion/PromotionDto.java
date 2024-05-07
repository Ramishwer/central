package com.goev.central.dto.promotion;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.region.RegionTypeDto;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionDto {
    private String uuid;
    private String status;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
    private DateTime validFromTime;
    private DateTime validToTime;
    private String type;
    private String platform;
    private String activationType;
}



