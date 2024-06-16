package com.goev.central.dto.promotion;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionDetailDto {
    private PromotionDto promotion;
    private String uuid;
    private String name;
    private String description;
    private String terms;
    private String restrictions;
    private Integer amount;
    private String amountType;
    private String isExclusive;
}



