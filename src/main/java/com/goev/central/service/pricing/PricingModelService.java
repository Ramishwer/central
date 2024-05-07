package com.goev.central.service.pricing;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.pricing.PricingModelDto;

public interface PricingModelService {
    PaginatedResponseDto<PricingModelDto> getPricingModels();

    PricingModelDto createPricingModel(PricingModelDto pricingModelDto);

    PricingModelDto updatePricingModel(String pricingModelUUID, PricingModelDto pricingModelDto);

    PricingModelDto getPricingModelDetails(String pricingModelUUID);

    Boolean deletePricingModel(String pricingModelUUID);
}
