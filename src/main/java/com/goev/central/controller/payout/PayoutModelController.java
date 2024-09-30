package com.goev.central.controller.payout;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payout.PayoutModelDto;
import com.goev.central.service.payout.PayoutModelService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/payout-management")
@AllArgsConstructor
public class PayoutModelController {

    private final PayoutModelService payoutModelService;

    @GetMapping("/payout-models")
    public ResponseDto<PaginatedResponseDto<PayoutModelDto>> getPayoutModels(@RequestParam(value = "count",required = false) Integer count,
                                                                             @RequestParam(value ="start",required = false) Integer start,
                                                                             @RequestParam(value ="from",required = false) Long from,
                                                                             @RequestParam(value ="to",required = false) Long to,
                                                                             @RequestParam(value ="lastUUID",required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, payoutModelService.getPayoutModels());
    }


    @GetMapping("/payout-models/{model-uuid}")
    public ResponseDto<PayoutModelDto> getPayoutModelDetails(@PathVariable(value = "model-uuid") String modelUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, payoutModelService.getPayoutModelDetails(modelUUID));
    }


    @PostMapping("/payout-models")
    public ResponseDto<PayoutModelDto> createPayoutModel(@RequestBody PayoutModelDto payoutModelDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, payoutModelService.createPayoutModel(payoutModelDto));
    }

    @PutMapping("/payout-models/{model-uuid}")
    public ResponseDto<PayoutModelDto> updatePayoutModel(@PathVariable(value = "model-uuid") String modelUUID, @RequestBody PayoutModelDto payoutModelDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, payoutModelService.updatePayoutModel(modelUUID, payoutModelDto));
    }

    @DeleteMapping("/payout-models/{model-uuid}")
    public ResponseDto<Boolean> deletePayoutModel(@PathVariable(value = "model-uuid") String modelUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, payoutModelService.deletePayoutModel(modelUUID));
    }
}
