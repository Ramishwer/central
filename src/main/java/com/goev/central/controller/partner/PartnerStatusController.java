package com.goev.central.controller.partner;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.service.partner.detail.PartnerService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerStatusController {

    private final PartnerService partnerService;


    @GetMapping("/partners/status")
    public ResponseDto<PaginatedResponseDto<PartnerDto>> getPartnerStatus(@RequestParam("dutyStatus")String status) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerService.getPartnerStatus(status));
    }
}
