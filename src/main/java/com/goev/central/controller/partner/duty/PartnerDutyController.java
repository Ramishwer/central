package com.goev.central.controller.partner.duty;

import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.service.partner.duty.PartnerDutyService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerDutyController {
    private final PartnerDutyService partnerDutyService;

    @GetMapping("/partners/duties")
    public ResponseDto<PaginatedResponseDto<PartnerDutyDto>> getDuties(@RequestParam("status")String status, PageDto page, FilterDto filter) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDutyService.getDuties(status,page,filter));
    }

    @GetMapping("/partners/{partner-uuid}/duties/{duty-uuid}")
    public ResponseDto<PartnerDutyDto> getDutyDetails(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "duty-uuid") String dutyUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDutyService.getDutyDetails(partnerUUID, dutyUUID));
    }

}
