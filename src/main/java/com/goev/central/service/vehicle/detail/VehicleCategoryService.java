package com.goev.central.service.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleCategoryDto;

public interface VehicleCategoryService {
    PaginatedResponseDto<VehicleCategoryDto> getCategories();
    VehicleCategoryDto createCategory(VehicleCategoryDto vehicleCategoryDto);
    VehicleCategoryDto updateCategory(String categoryUUID, VehicleCategoryDto vehicleCategoryDto);
    VehicleCategoryDto getCategoryDetails(String categoryUUID);
    Boolean deleteCategory(String categoryUUID);

}
