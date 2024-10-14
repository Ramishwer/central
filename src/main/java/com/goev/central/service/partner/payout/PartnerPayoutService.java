package com.goev.central.service.partner.payout;

import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.payout.*;

import java.util.List;

public interface PartnerPayoutService {
    PaginatedResponseDto<PartnerPayoutDto> getPayoutsForPartner(String partnerUUID);

    PartnerPayoutSummaryDto getPayoutSummaryForPayout(String partnerUUID, String partnerPayoutUUID);

    PaginatedResponseDto<PartnerPayoutTransactionDto> getAllPartnerTransactionsForPayout(String partnerUUID, String partnerPayoutUUID);

    PaginatedResponseDto<PartnerPayoutDto> getPayouts(String status, PageDto page, FilterDto filter);

    List<PartnerPayoutMappingDto> getPayoutModelMappings(String partnerUUID);

    PartnerPayoutMappingDto createPayoutModelMapping(String partnerUUID, PartnerPayoutMappingDto partnerPayoutMappingDto);

    Boolean deletePayoutModelMapping(String partnerUUID, String partnerPayoutModelMappingUUID);

    PaginatedResponseDto<PartnerCreditDebitTransactionDto> getPartnerPayoutCreditDebitTransaction(String partnerUUID, String partnerPayoutUUID);

    PartnerCreditDebitTransactionDto savePartnerPayoutCreditDebitTransaction(String partnerUUID, String partnerPayoutUUID, PartnerCreditDebitTransactionDto partnerCreditDebitTransactionDto);

    Boolean deletePartnerPayoutCreditDebitTransaction(String partnerUUID, String partnerPayoutUUID, String partnerCreditDebitTransactionUUID);
}
