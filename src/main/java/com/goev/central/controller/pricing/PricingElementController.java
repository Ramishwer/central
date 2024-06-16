package com.goev.central.controller.pricing;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.pricing.PricingElementDto;
import com.goev.central.service.pricing.PricingElementService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/pricing-management")
@AllArgsConstructor
public class PricingElementController {

    private final PricingElementService pricingElementService;

    @GetMapping("/pricing-elements")
    public ResponseDto<PaginatedResponseDto<PricingElementDto>> getPricingElements(@RequestParam("count") Integer count,
                                                                                   @RequestParam("start") Integer start,
                                                                                   @RequestParam("from") Long from,
                                                                                   @RequestParam("to") Long to,
                                                                                   @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, pricingElementService.getPricingElements());
    }


    @GetMapping("/pricing-elements/{element-uuid}")
    public ResponseDto<PricingElementDto> getPricingElementDetails(@PathVariable(value = "element-uuid") String elementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, pricingElementService.getPricingElementDetails(elementUUID));
    }


    @PostMapping("/pricing-elements")
    public ResponseDto<PricingElementDto> createPricingElement(@RequestBody PricingElementDto pricingElementDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, pricingElementService.createPricingElement(pricingElementDto));
    }

    @PutMapping("/pricing-elements/{element-uuid}")
    public ResponseDto<PricingElementDto> updatePricingElement(@PathVariable(value = "element-uuid") String elementUUID, @RequestBody PricingElementDto pricingElementDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, pricingElementService.updatePricingElement(elementUUID, pricingElementDto));
    }

    @DeleteMapping("/pricing-elements/{element-uuid}")
    public ResponseDto<Boolean> deletePricingElement(@PathVariable(value = "element-uuid") String elementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, pricingElementService.deletePricingElement(elementUUID));
    }
}
