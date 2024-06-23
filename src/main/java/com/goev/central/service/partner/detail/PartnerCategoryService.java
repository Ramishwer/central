package com.goev.central.service.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerCategoryDto;

public interface PartnerCategoryService {
    PaginatedResponseDto<PartnerCategoryDto> getCategories();

    PartnerCategoryDto createCategory(PartnerCategoryDto partnerCategoryDto);

    PartnerCategoryDto updateCategory(String categoryUUID, PartnerCategoryDto partnerCategoryDto);

    PartnerCategoryDto getCategoryDetails(String categoryUUID);

    Boolean deleteCategory(String categoryUUID);

}
