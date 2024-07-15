package com.goev.central.controller.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.ActionDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDto;
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
    public ResponseDto<PaginatedResponseDto<PartnerViewDto>> getPartners(@RequestParam(value = "onboardingStatus", required = false) String onboardingStatus) {
        if (onboardingStatus == null)
            return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerService.getPartners());
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerService.getPartners(onboardingStatus));
    }


    @DeleteMapping("/partners/{partner-uuid}")
    public ResponseDto<Boolean> deleteAccount(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerService.deletePartner(partnerUUID));
    }

    @PostMapping("/partners/{partner-uuid}")
    public ResponseDto<PartnerDto> updatePartner(@PathVariable("partner-uuid") String partnerUUID, @RequestBody ActionDto actionDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerService.updatePartner(partnerUUID, actionDto));
    }
}
