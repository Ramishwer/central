package com.goev.central.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.pricing.PricingElementDto;
import com.goev.central.dto.pricing.PricingModelDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingPricingDetailDto {
    private String uuid;
    private BookingViewDto booking;
    private Integer totalAmount;
    private Integer totalAmountExcludingTax;
    private Integer taxAmount;
    private Integer taxPercentage;
    private Integer discountAmount;
    private String type;
    private String status;
    private PricingModelDto pricingModel;
}
