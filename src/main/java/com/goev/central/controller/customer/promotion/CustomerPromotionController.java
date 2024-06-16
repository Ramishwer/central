package com.goev.central.controller.customer.promotion;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.promotion.CustomerPromotionDto;
import com.goev.central.service.customer.promotion.CustomerPromotionService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerPromotionController {

    private final CustomerPromotionService customerPromotionService;

    @GetMapping("/customer/{customer-uuid}/promotions")
    public ResponseDto<PaginatedResponseDto<CustomerPromotionDto>> getCustomerPromotions(@PathVariable(value = "customer-uuid") String customerUUID,
                                                                                         @RequestParam(value = "count", required = false) Integer count,
                                                                                         @RequestParam(value = "start", required = false) Integer start,
                                                                                         @RequestParam(value = "from", required = false) Long from,
                                                                                         @RequestParam(value = "to", required = false) Long to,
                                                                                         @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerPromotionService.getCustomerPromotions(customerUUID));
    }


    @GetMapping("/customer/{customer-uuid}/promotions/{promotion-uuid}")
    public ResponseDto<CustomerPromotionDto> getCustomerPromotionDetails(@PathVariable(value = "customer-uuid") String customerUUID, @PathVariable(value = "promotion-uuid") String promotionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerPromotionService.getCustomerPromotionDetails(customerUUID, promotionUUID));
    }


    @PostMapping("/customer/{customer-uuid}/promotions")
    public ResponseDto<CustomerPromotionDto> createCustomerPromotion(@PathVariable(value = "customer-uuid") String customerUUID, @RequestBody CustomerPromotionDto customerPromotionDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerPromotionService.createCustomerPromotion(customerUUID, customerPromotionDto));
    }

    @DeleteMapping("/customer/{customer-uuid}/promotions/{promotion-uuid}")
    public ResponseDto<Boolean> deleteCustomerPromotion(@PathVariable(value = "customer-uuid") String customerUUID, @PathVariable(value = "promotion-uuid") String promotionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerPromotionService.deleteCustomerPromotion(customerUUID, promotionUUID));
    }
}
