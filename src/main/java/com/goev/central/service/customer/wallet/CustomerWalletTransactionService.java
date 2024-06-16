package com.goev.central.service.customer.wallet;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.wallet.CustomerWalletTransactionDto;

public interface CustomerWalletTransactionService {
    PaginatedResponseDto<CustomerWalletTransactionDto> getCustomerWalletTransactions(String customerUUID);

    CustomerWalletTransactionDto getCustomerWalletTransactionDetails(String customerUUID, String customerWalletTransactionUUID);
}
