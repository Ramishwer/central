package com.goev.central.controller.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerCategoryDto;
import com.goev.central.service.partner.detail.PartnerCategoryService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerCategoryController {
    private final PartnerCategoryService partnerCategoryService;

    @GetMapping("/partners/categories")
    public ResponseDto<PaginatedResponseDto<PartnerCategoryDto>> getCategories() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerCategoryService.getCategories());
    }

    @PostMapping("/partners/categories")
    public ResponseDto<PartnerCategoryDto> createCategory(@RequestBody PartnerCategoryDto partnerCategoryDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerCategoryService.createCategory(partnerCategoryDto));
    }

    @PutMapping("/partners/categories/{category-uuid}")
    public ResponseDto<PartnerCategoryDto> updateCategory(@PathVariable(value = "category-uuid") String categoryUUID, @RequestBody PartnerCategoryDto partnerCategoryDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerCategoryService.updateCategory(categoryUUID, partnerCategoryDto));
    }

    @GetMapping("/partners/categories/{category-uuid}")
    public ResponseDto<PartnerCategoryDto> getCategoryDetails(@PathVariable(value = "category-uuid") String categoryUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerCategoryService.getCategoryDetails(categoryUUID));
    }

    @DeleteMapping("/partners/categories/{category-uuid}")
    public ResponseDto<Boolean> deleteCategory(@PathVariable(value = "category-uuid") String categoryUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerCategoryService.deleteCategory(categoryUUID));
    }
}
