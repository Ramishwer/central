package com.goev.central.service.partner.payout;

import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.payout.PartnerPayoutDto;
import com.goev.central.dto.partner.payout.PartnerPayoutMappingDto;
import com.goev.central.dto.partner.payout.PartnerPayoutSummaryDto;
import com.goev.central.dto.partner.payout.PartnerPayoutTransactionDto;

import java.util.List;

public interface PartnerPayoutService {
    PaginatedResponseDto<PartnerPayoutDto> getPayoutsForPartner(String partnerUUID);

    PartnerPayoutSummaryDto getPayoutSummaryForPayout(String partnerUUID, String partnerPayoutUUID);

    PaginatedResponseDto<PartnerPayoutTransactionDto> getAllPartnerTransactionsForPayout(String partnerUUID, String partnerPayoutUUID);

    PaginatedResponseDto<PartnerPayoutDto> getPayouts(String status, PageDto page, FilterDto filter);

    List<PartnerPayoutMappingDto> getPayoutModelMappings(String partnerUUID);

    PartnerPayoutMappingDto createPayoutModelMapping(String partnerUUID, PartnerPayoutMappingDto partnerPayoutMappingDto);

    Boolean deletePayoutModelMapping(String partnerUUID, String partnerPayoutModelMappingUUID);
}
