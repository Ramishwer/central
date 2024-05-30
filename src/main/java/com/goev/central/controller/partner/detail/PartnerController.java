package com.goev.central.controller.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.service.partner.detail.PartnerService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerController {


    private final PartnerService partnerService;


    @GetMapping("/partners")
    public ResponseDto<PaginatedResponseDto<PartnerViewDto>> getPartners() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerService.getPartners());
    }


    @DeleteMapping("/partners/{partner-uuid}")
    public ResponseDto<Boolean> deleteAccount(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerService.deletePartner(partnerUUID));
    }
}
