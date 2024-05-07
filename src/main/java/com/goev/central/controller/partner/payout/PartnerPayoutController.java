package com.goev.central.controller.partner.payout;

import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.payout.PartnerPayoutDto;
import com.goev.central.dto.partner.payout.PartnerPayoutSummaryDto;
import com.goev.central.dto.partner.payout.PartnerPayoutTransactionDto;
import com.goev.central.service.partner.payout.PartnerPayoutService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerPayoutController {

    private final PartnerPayoutService partnerPayoutService;
    @GetMapping("/partners/{partner-uuid}/payouts")
    public ResponseDto<PaginatedResponseDto<PartnerPayoutDto>> getPayoutsForPartner(@PathVariable("partner-uuid")String partnerUUID,
                                                                                    @RequestParam("count")Integer count,
                                                                                    @RequestParam("start")Integer start){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerPayoutService.getPayouts(partnerUUID));
    }

    @GetMapping("/partners/{partner-uuid}/payouts/{partner-payout-uuid}/summary")
    public ResponseDto<PartnerPayoutSummaryDto> getPayoutSummaryForPartnerAndPayout(@PathVariable("partner-uuid")String partnerUUID,
                                                                                    @PathVariable("partner-payout-uuid")String partnerPayoutUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerPayoutService.getPayoutSummaryForPayout(partnerUUID,partnerPayoutUUID));
    }

    @GetMapping("/partners/{partner-uuid}/payouts/{partner-payout-uuid}/transactions")
    public ResponseDto<PaginatedResponseDto<PartnerPayoutTransactionDto>> getPayoutTransactionsForPartnerAndPayout(@PathVariable("partner-uuid")String partnerUUID,
                                                                                             @PathVariable("partner-payout-uuid")String partnerPayoutUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerPayoutService.getAllPartnerTransactionsForPayout(partnerUUID,partnerPayoutUUID));
    }

}
