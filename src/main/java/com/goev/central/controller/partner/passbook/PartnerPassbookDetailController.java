package com.goev.central.controller.partner.passbook;

import com.goev.central.dto.partner.passbook.PartnerPassbookDetailDto;
import com.goev.central.service.partner.passbook.PartnerPassbookDetailService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerPassbookDetailController {

    private final PartnerPassbookDetailService partnerPassbookDetailService;


    @GetMapping("/partners/{partner-uuid}/passbooks")
    public ResponseDto<PartnerPassbookDetailDto> getPartnerPassbookDetails(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerPassbookDetailService.getPartnerPassbookDetails(partnerUUID));
    }

}
