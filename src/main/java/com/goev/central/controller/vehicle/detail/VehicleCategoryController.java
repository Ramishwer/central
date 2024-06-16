package com.goev.central.controller.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleCategoryDto;
import com.goev.central.service.vehicle.detail.VehicleCategoryService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleCategoryController {
    private final VehicleCategoryService vehicleCategoryService;

    @GetMapping("/categories")
    public ResponseDto<PaginatedResponseDto<VehicleCategoryDto>> getCategories() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleCategoryService.getCategories());
    }

    @PostMapping("/categories")
    public ResponseDto<VehicleCategoryDto> createCategory(@RequestBody VehicleCategoryDto vehicleCategoryDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleCategoryService.createCategory(vehicleCategoryDto));
    }

    @PutMapping("/categories/{category-uuid}")
    public ResponseDto<VehicleCategoryDto> updateCategory(@PathVariable(value = "category-uuid") String categoryUUID, @RequestBody VehicleCategoryDto vehicleCategoryDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleCategoryService.updateCategory(categoryUUID, vehicleCategoryDto));
    }

    @GetMapping("/categories/{category-uuid}")
    public ResponseDto<VehicleCategoryDto> getCategoryDetails(@PathVariable(value = "category-uuid") String categoryUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleCategoryService.getCategoryDetails(categoryUUID));
    }

    @DeleteMapping("/categories/{category-uuid}")
    public ResponseDto<Boolean> deleteCategory(@PathVariable(value = "category-uuid") String categoryUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleCategoryService.deleteCategory(categoryUUID));
    }
}
