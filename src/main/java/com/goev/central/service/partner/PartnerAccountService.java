package com.goev.central.service.partner;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerAccountDto;

public interface PartnerAccountService {
    PaginatedResponseDto<PartnerAccountDto> getAccounts(String partnerUUID);
    PartnerAccountDto createAccount(String partnerUUID,PartnerAccountDto partnerAccountDto);
    PartnerAccountDto updateAccount(String partnerUUID,String accountUUID, PartnerAccountDto partnerAccountDto);
    PartnerAccountDto getAccountDetails(String partnerUUID,String accountUUID);
    Boolean deleteAccount(String partnerUUID,String accountUUID);

}
