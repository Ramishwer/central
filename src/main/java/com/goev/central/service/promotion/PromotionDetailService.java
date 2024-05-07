package com.goev.central.service.promotion;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.promotion.PromotionDetailDto;

public interface PromotionDetailService {
    PromotionDetailDto createPromotionDetail(PromotionDetailDto promotionDetailDto);

    PromotionDetailDto getPromotionDetailDetails(String promotionDetailUUID);
}
