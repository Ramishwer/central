package com.goev.central.controller.customer.wallet;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.wallet.CustomerWalletTransactionDto;
import com.goev.central.service.customer.wallet.CustomerWalletTransactionService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerWalletTransactionController {

    private final CustomerWalletTransactionService customerWalletTransactionService;

    @GetMapping("/customers/{customer-uuid}/wallets/transactions")
    public ResponseDto<PaginatedResponseDto<CustomerWalletTransactionDto>> getCustomerWalletTransactions(
            @PathVariable(value = "customer-uuid") String customerUUID,
            @RequestParam("count") Integer count,
            @RequestParam("start") Integer start,
            @RequestParam("from") Long from,
            @RequestParam("to") Long to,
            @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerWalletTransactionService.getCustomerWalletTransactions(customerUUID));
    }


    @GetMapping("/customers/{customer-uuid}/wallets/transactions/{wallet-transaction-uuid}")
    public ResponseDto<CustomerWalletTransactionDto> getCustomerWalletTransactionDetails(@PathVariable(value = "customer-uuid") String customerUUID, @PathVariable(value = "wallet-transaction-uuid") String walletTransactionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerWalletTransactionService.getCustomerWalletTransactionDetails(customerUUID, walletTransactionUUID));
    }

}
