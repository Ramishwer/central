package com.goev.central.service.customer.promotion;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.promotion.CustomerPromotionDto;

public interface CustomerPromotionService {
    PaginatedResponseDto<CustomerPromotionDto> getCustomerPromotions(String customerUUID);

    CustomerPromotionDto createCustomerPromotion(String customerUUID,CustomerPromotionDto customerPromotionDto);

    CustomerPromotionDto getCustomerPromotionDetails(String customerUUID,String customerPromotionUUID);

    Boolean deleteCustomerPromotion(String customerUUID,String customerPromotionUUID);
}
