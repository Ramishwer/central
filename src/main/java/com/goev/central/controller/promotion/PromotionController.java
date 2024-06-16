package com.goev.central.controller.promotion;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.promotion.PromotionDto;
import com.goev.central.service.promotion.PromotionService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/promotion-management")
@AllArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping("/promotions")
    public ResponseDto<PaginatedResponseDto<PromotionDto>> getPromotions() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, promotionService.getPromotions());
    }

    @PostMapping("/promotions")
    public ResponseDto<PromotionDto> createPromotion(@RequestBody PromotionDto promotionDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, promotionService.createPromotion(promotionDto));
    }

    @PutMapping("/promotions/{promotion-uuid}")
    public ResponseDto<PromotionDto> updatePromotion(@PathVariable(value = "promotion-uuid") String promotionUUID, @RequestBody PromotionDto promotionDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, promotionService.updatePromotion(promotionUUID, promotionDto));
    }

    @GetMapping("/promotions/{promotion-uuid}")
    public ResponseDto<PromotionDto> getPromotionDetails(@PathVariable(value = "promotion-uuid") String promotionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, promotionService.getPromotionDetails(promotionUUID));
    }

    @DeleteMapping("/promotions/{promotion-uuid}")
    public ResponseDto<Boolean> deletePromotion(@PathVariable(value = "promotion-uuid") String promotionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, promotionService.deletePromotion(promotionUUID));
    }
}
