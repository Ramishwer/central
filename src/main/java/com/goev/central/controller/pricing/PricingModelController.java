package com.goev.central.controller.pricing;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.pricing.PricingModelDto;
import com.goev.central.service.pricing.PricingModelService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/pricing-management")
@AllArgsConstructor
public class PricingModelController {

    private final PricingModelService pricingModelService;

    @GetMapping("/pricing-models")
    public ResponseDto<PaginatedResponseDto<PricingModelDto>> getPricingModels(@RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, pricingModelService.getPricingModels());
    }

    

    @GetMapping("/pricing-models/{model-uuid}")
    public ResponseDto<PricingModelDto> getPricingModelDetails(@PathVariable(value = "model-uuid")String modelUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, pricingModelService.getPricingModelDetails(modelUUID));
    }


    @PostMapping("/pricing-models")
    public ResponseDto<PricingModelDto> createPricingModel(@RequestBody PricingModelDto pricingModelDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, pricingModelService.createPricingModel(pricingModelDto));
    }

    @PutMapping("/pricing-models/{model-uuid}")
    public ResponseDto<PricingModelDto> updatePricingModel(@PathVariable(value = "model-uuid")String modelUUID, @RequestBody PricingModelDto pricingModelDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, pricingModelService.updatePricingModel(modelUUID,pricingModelDto));
    }
    @DeleteMapping("/pricing-models/{model-uuid}")
    public ResponseDto<Boolean> deletePricingModel(@PathVariable(value = "model-uuid")String modelUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, pricingModelService.deletePricingModel(modelUUID));
    }
}
