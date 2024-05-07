package com.goev.central.controller.payout;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payout.PayoutModelConfigurationDto;
import com.goev.central.service.payout.PayoutModelConfigurationService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/payout-management")
@AllArgsConstructor
public class PayoutModelConfigurationController {

    private final PayoutModelConfigurationService payoutModelConfigurationService;

    @GetMapping("/payout-models/{model-uuid}/configurations")
    public ResponseDto<PaginatedResponseDto<PayoutModelConfigurationDto>> getPayoutModelConfigurations(@PathVariable(value = "model-uuid") String modelUUID, @RequestParam("count") Integer count,
                                                                                                       @RequestParam("start") Integer start,
                                                                                                       @RequestParam("from") Long from,
                                                                                                       @RequestParam("to") Long to,
                                                                                                       @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, payoutModelConfigurationService.getPayoutModelConfigurations(modelUUID));
    }


    @GetMapping("/payout-models/{model-uuid}/configurations/{configuration-uuid}")
    public ResponseDto<PayoutModelConfigurationDto> getPayoutModelConfigurationDetails(@PathVariable(value = "model-uuid") String modelUUID, @PathVariable(value = "configuration-uuid") String configurationUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, payoutModelConfigurationService.getPayoutModelConfigurationDetails(modelUUID,configurationUUID));
    }


    @PostMapping("/payout-models/{model-uuid}/configurations")
    public ResponseDto<PayoutModelConfigurationDto> createPayoutModelConfiguration(@PathVariable(value = "model-uuid") String modelUUID, @RequestBody PayoutModelConfigurationDto payoutModelConfigurationDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, payoutModelConfigurationService.createPayoutModelConfiguration(modelUUID,payoutModelConfigurationDto));
    }

    @PutMapping("/payout-models/{model-uuid}/configurations/{configuration-uuid}")
    public ResponseDto<PayoutModelConfigurationDto> updatePayoutModelConfiguration(@PathVariable(value = "model-uuid") String modelUUID, @PathVariable(value = "configuration-uuid") String configurationUUID, @RequestBody PayoutModelConfigurationDto payoutModelConfigurationDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, payoutModelConfigurationService.updatePayoutModelConfiguration(modelUUID,configurationUUID, payoutModelConfigurationDto));
    }

    @DeleteMapping("/payout-models/{model-uuid}/configurations/{configuration-uuid}")
    public ResponseDto<Boolean> deletePayoutModelConfiguration(@PathVariable(value = "model-uuid") String modelUUID, @PathVariable(value = "configuration-uuid") String configurationUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, payoutModelConfigurationService.deletePayoutModelConfiguration(modelUUID, configurationUUID));
    }
}
