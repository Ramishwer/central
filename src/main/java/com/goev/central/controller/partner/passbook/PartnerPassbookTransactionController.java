package com.goev.central.controller.partner.passbook;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.passbook.PartnerPassbookTransactionDto;
import com.goev.central.service.partner.passbook.PartnerPassbookTransactionService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerPassbookTransactionController {

    private final PartnerPassbookTransactionService partnerPassbookTransactionService;

    @GetMapping("/partners/{partner-uuid}/passbooks/transactions")
    public ResponseDto<PaginatedResponseDto<PartnerPassbookTransactionDto>> getPartnerPassbookTransactions(
            @PathVariable(value = "partner-uuid") String partnerUUID,
            @RequestParam("count") Integer count,
            @RequestParam("start") Integer start,
            @RequestParam("from") Long from,
            @RequestParam("to") Long to,
            @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerPassbookTransactionService.getPartnerPassbookTransactions(partnerUUID));
    }


    @GetMapping("/partners/{partner-uuid}/passbooks/transactions/{passbook-transaction-uuid}")
    public ResponseDto<PartnerPassbookTransactionDto> getPartnerPassbookTransactionDetails(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "passbook-transaction-uuid") String passbookTransactionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerPassbookTransactionService.getPartnerPassbookTransactionDetails(partnerUUID, passbookTransactionUUID));
    }

}
