package com.goev.central.service.partner.payout;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.payout.PartnerPayoutDto;
import com.goev.central.dto.partner.payout.PartnerPayoutSummaryDto;
import com.goev.central.dto.partner.payout.PartnerPayoutTransactionDto;

public interface PartnerPayoutService {
    PaginatedResponseDto<PartnerPayoutDto> getPayoutsForPartner(String partnerUUID);

    PartnerPayoutSummaryDto getPayoutSummaryForPayout(String partnerUUID, String partnerPayoutUUID);

    PaginatedResponseDto<PartnerPayoutTransactionDto> getAllPartnerTransactionsForPayout(String partnerUUID, String partnerPayoutUUID);

    PaginatedResponseDto<PartnerPayoutDto> getPayouts();
}
