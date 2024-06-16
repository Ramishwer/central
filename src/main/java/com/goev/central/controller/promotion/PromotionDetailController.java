package com.goev.central.controller.promotion;

import com.goev.central.dto.promotion.PromotionDetailDto;
import com.goev.central.service.promotion.PromotionDetailService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/promotion-management")
@AllArgsConstructor
public class PromotionDetailController {

    private final PromotionDetailService promotionDetailService;


    @PostMapping("/promotions/details")
    public ResponseDto<PromotionDetailDto> createPromotion(@RequestBody PromotionDetailDto promotionDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, promotionDetailService.createPromotionDetail(promotionDto));
    }


    @GetMapping("/promotions/{promotion-uuid}/details")
    public ResponseDto<PromotionDetailDto> getPromotionDetails(@PathVariable(value = "promotion-uuid") String promotionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, promotionDetailService.getPromotionDetailDetails(promotionUUID));
    }

}
