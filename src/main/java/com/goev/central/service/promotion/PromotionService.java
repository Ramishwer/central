package com.goev.central.service.promotion;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.promotion.PromotionDto;

public interface PromotionService {
    PaginatedResponseDto<PromotionDto> getPromotions();

    PromotionDto createPromotion(PromotionDto promotionDto);

    PromotionDto updatePromotion(String promotionUUID, PromotionDto promotionDto);

    PromotionDto getPromotionDetails(String promotionUUID);

    Boolean deletePromotion(String promotionUUID);
}
