package com.goev.central.dto.pricing;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PricingElementDto {
    private String uuid;
    private PricingModelDto pricingModel;
    private String label;
    private String name;
    private String type;
    private String description;
    private String rules;
    private String title;
    private String subtitle;
    private String message;
    private String category;
    private Integer sortingOrder;
}



