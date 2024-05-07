package com.goev.central.service.partner.passbook;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.passbook.PartnerPassbookTransactionDto;

public interface PartnerPassbookTransactionService {
    PaginatedResponseDto<PartnerPassbookTransactionDto> getPartnerPassbookTransactions(String partnerUUID);
    PartnerPassbookTransactionDto getPartnerPassbookTransactionDetails(String partnerUUID, String partnerPassbookTransactionUUID);
}
