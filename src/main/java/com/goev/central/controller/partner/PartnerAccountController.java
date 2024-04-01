package com.goev.central.controller.partner;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerAccountDto;
import com.goev.central.service.partner.PartnerAccountService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management/partners")
@AllArgsConstructor
public class PartnerAccountController {

    private final PartnerAccountService partnerAccountService;

    @GetMapping("{partner-uuid}/accounts")
    public ResponseDto<PaginatedResponseDto<PartnerAccountDto>> getAccounts(@PathVariable(value = "partner-uuid")String partnerUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAccountService.getAccounts(partnerUUID));
    }
    @PostMapping("{partner-uuid}/accounts")
    public ResponseDto<PartnerAccountDto> createAccount(@PathVariable(value = "partner-uuid")String partnerUUID,@RequestBody PartnerAccountDto partnerAccountDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAccountService.createAccount(partnerUUID,partnerAccountDto));
    }

    @PutMapping("{partner-uuid}/accounts/{account-uuid}")
    public ResponseDto<PartnerAccountDto> updateAccount(@PathVariable(value = "partner-uuid")String partnerUUID,@PathVariable(value = "account-uuid")String accountUUID, @RequestBody PartnerAccountDto partnerAccountDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAccountService.updateAccount(partnerUUID,accountUUID,partnerAccountDto));
    }

    @GetMapping("{partner-uuid}/accounts/{account-uuid}")
    public ResponseDto<PartnerAccountDto> getAccountDetails(@PathVariable(value = "partner-uuid")String partnerUUID,@PathVariable(value = "account-uuid")String accountUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAccountService.getAccountDetails(partnerUUID,accountUUID));
    }

    @DeleteMapping("{partner-uuid}/accounts/{account-uuid}")
    public ResponseDto<Boolean> deleteAccount(@PathVariable(value = "partner-uuid")String partnerUUID,@PathVariable(value = "account-uuid")String accountUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAccountService.deleteAccount(partnerUUID,accountUUID));
    }
}
