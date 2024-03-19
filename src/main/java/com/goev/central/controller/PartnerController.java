package com.goev.central.controller;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.CustomerDto;
import com.goev.central.dto.partner.PartnerDetailsDto;
import com.goev.central.dto.partner.PartnerDto;
import com.goev.central.dto.session.SessionDetailsDto;
import com.goev.central.dto.session.SessionDto;
import com.goev.central.service.PartnerService;
import com.goev.central.service.SessionService;
import com.goev.lib.dto.PasswordCredentialsDto;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @GetMapping("/partners")
    public ResponseDto<PaginatedResponseDto<PartnerDto>> getPartners(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerService.getPartners());
    }
    @PostMapping("/partners")
    public ResponseDto<PartnerDetailsDto> createPartner(@RequestBody PartnerDetailsDto partnerDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerService.createPartner(partnerDto));
    }

    @PutMapping("/partners/{partner-uuid}")
    public ResponseDto<PartnerDetailsDto> updatePartner(@PathVariable(value = "partner-uuid")String partnerUUID,@RequestBody PartnerDetailsDto partnerDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerService.updatePartner(partnerUUID,partnerDto));
    }

    @GetMapping("/partners/{partner-uuid}")
    public ResponseDto<PartnerDetailsDto> getPartnerDetails(@PathVariable(value = "partner-uuid")String partnerUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerService.getPartnerDetails(partnerUUID));
    }

    @DeleteMapping("/partners/{partner-uuid}")
    public ResponseDto<Boolean> deletePartner(@PathVariable(value = "partner-uuid")String partnerUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerService.deletePartner(partnerUUID));
    }
}
