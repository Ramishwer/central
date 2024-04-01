package com.goev.central.controller.partner;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDetailsDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.service.partner.PartnerService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping("/partners")
    public ResponseDto<PaginatedResponseDto<PartnerViewDto>> getPartners(){
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
