package com.goev.central.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.pricing.PricingElementDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingPricingBreakupDetailDto {
    private String uuid;
    private BookingPricingDetailDto bookingPricingDetails;
    private String elementName;
    private String elementType;
    private String elementAmount;
    private BookingDto booking;
    private String triggerType;
    private PricingElementDto pricingElement;
}
