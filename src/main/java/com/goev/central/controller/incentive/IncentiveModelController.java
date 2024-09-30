package com.goev.central.controller.incentive;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.incentive.IncentiveModelDto;
import com.goev.central.service.incentive.IncentiveModelService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/incentive-management")
@AllArgsConstructor
public class IncentiveModelController {

    private final IncentiveModelService incentiveModelService;

    @GetMapping("/incentive-models")
    public ResponseDto<PaginatedResponseDto<IncentiveModelDto>> getIncentiveModels(@RequestParam(value = "count",required = false) Integer count,
                                                                                   @RequestParam(value ="start",required = false) Integer start,
                                                                                   @RequestParam(value ="from",required = false) Long from,
                                                                                   @RequestParam(value ="to",required = false) Long to,
                                                                                   @RequestParam(value ="lastUUID",required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, incentiveModelService.getIncentiveModels());
    }


    @GetMapping("/incentive-models/{model-uuid}")
    public ResponseDto<IncentiveModelDto> getIncentiveModelDetails(@PathVariable(value = "model-uuid") String modelUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, incentiveModelService.getIncentiveModelDetails(modelUUID));
    }


    @PostMapping("/incentive-models")
    public ResponseDto<IncentiveModelDto> createIncentiveModel(@RequestBody IncentiveModelDto incentiveModelDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, incentiveModelService.createIncentiveModel(incentiveModelDto));
    }

    @PutMapping("/incentive-models/{model-uuid}")
    public ResponseDto<IncentiveModelDto> updateIncentiveModel(@PathVariable(value = "model-uuid") String modelUUID, @RequestBody IncentiveModelDto incentiveModelDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, incentiveModelService.updateIncentiveModel(modelUUID, incentiveModelDto));
    }

    @DeleteMapping("/incentive-models/{model-uuid}")
    public ResponseDto<Boolean> deleteIncentiveModel(@PathVariable(value = "model-uuid") String modelUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, incentiveModelService.deleteIncentiveModel(modelUUID));
    }
}
