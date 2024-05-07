package com.goev.central.service.pricing;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.pricing.PricingElementDto;

public interface PricingElementService {
    PaginatedResponseDto<PricingElementDto> getPricingElements();

    PricingElementDto createPricingElement(PricingElementDto pricingElementDto);

    PricingElementDto updatePricingElement(String pricingElementUUID, PricingElementDto pricingElementDto);

    PricingElementDto getPricingElementDetails(String pricingElementUUID);

    Boolean deletePricingElement(String pricingElementUUID);
}
