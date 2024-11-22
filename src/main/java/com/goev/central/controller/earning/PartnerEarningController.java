package com.goev.central.controller.earning;

import com.goev.central.dto.earning.EarningRuleDto;
import com.goev.central.dto.earning.PartnerEarningDto;
import com.goev.central.service.earning.PartnerEarningService;
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
@RequestMapping(value = "/api/v1/earning-management")
@AllArgsConstructor
public class PartnerEarningController {
    private final PartnerEarningService partnerEarningService;

    @GetMapping("/earning/partner/{partner-uuid}")
    public ResponseDto<PartnerEarningDto> getEarningRules(@PathVariable(value = "partner-uuid") String partnerUuid) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerEarningService.getPartnerEarningDetails(partnerUuid));
    }
}
