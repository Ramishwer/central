package com.goev.central.controller.partner.payout;

import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerShiftMappingDto;
import com.goev.central.dto.partner.payout.PartnerPayoutDto;
import com.goev.central.dto.partner.payout.PartnerPayoutMappingDto;
import com.goev.central.dto.partner.payout.PartnerPayoutSummaryDto;
import com.goev.central.dto.partner.payout.PartnerPayoutTransactionDto;
import com.goev.central.service.partner.payout.PartnerPayoutService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerPayoutController {

    private final PartnerPayoutService partnerPayoutService;


    @GetMapping("/partners/payouts")
    public ResponseDto<PaginatedResponseDto<PartnerPayoutDto>> getPayouts(@RequestParam("status")String status, PageDto page, FilterDto filter) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerPayoutService.getPayouts(status,page,filter));
    }

    @GetMapping("/partners/{partner-uuid}/payouts")
    public ResponseDto<PaginatedResponseDto<PartnerPayoutDto>> getPayoutsForPartner(@PathVariable("partner-uuid") String partnerUUID,
                                                                                    @RequestParam("count") Integer count,
                                                                                    @RequestParam("start") Integer start) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerPayoutService.getPayoutsForPartner(partnerUUID));
    }

    @GetMapping("/partners/{partner-uuid}/payouts/{partner-payout-uuid}/summary")
    public ResponseDto<PartnerPayoutSummaryDto> getPayoutSummaryForPartnerAndPayout(@PathVariable("partner-uuid") String partnerUUID,
                                                                                    @PathVariable("partner-payout-uuid") String partnerPayoutUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerPayoutService.getPayoutSummaryForPayout(partnerUUID, partnerPayoutUUID));
    }

    @GetMapping("/partners/{partner-uuid}/payouts/{partner-payout-uuid}/transactions")
    public ResponseDto<PaginatedResponseDto<PartnerPayoutTransactionDto>> getPayoutTransactionsForPartnerAndPayout(@PathVariable("partner-uuid") String partnerUUID,
                                                                                                                   @PathVariable("partner-payout-uuid") String partnerPayoutUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerPayoutService.getAllPartnerTransactionsForPayout(partnerUUID, partnerPayoutUUID));
    }



    @GetMapping("/partners/{partner-uuid}/payout-model-mappings")
    public ResponseDto<List<PartnerPayoutMappingDto>> getPayoutModelMappings(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerPayoutService.getPayoutModelMappings(partnerUUID));
    }
    @PostMapping("/partners/{partner-uuid}/payout-model-mappings")
    public ResponseDto<PartnerPayoutMappingDto> createPayoutModelMapping(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody PartnerPayoutMappingDto partnerPayoutMappingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerPayoutService.createPayoutModelMapping(partnerUUID, partnerPayoutMappingDto));
    }

    @DeleteMapping("/partners/{partner-uuid}/payout-model-mappings/{partner-payout-model-mapping-uuid}")
    public ResponseDto<Boolean> deletePayoutModelMapping(@PathVariable(value = "partner-uuid") String partnerUUID,@PathVariable(value = "partner-payout-model-mapping-uuid") String partnerPayoutModelMappingUUID)  {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerPayoutService.deletePayoutModelMapping(partnerUUID, partnerPayoutModelMappingUUID));
    }
}
