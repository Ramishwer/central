package com.goev.central.controller.partner.detail;

import com.goev.central.dto.partner.detail.PartnerDetailDto;
import com.goev.central.service.partner.detail.PartnerDetailService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerDetailController {


    private final PartnerDetailService partnerDetailService;

    @PostMapping("/partners/details")
    public ResponseDto<PartnerDetailDto> createPartner(@RequestBody PartnerDetailDto partnerDetailDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDetailService.createPartner(partnerDetailDto));
    }

    @PutMapping("/partners/{partner-uuid}/details")
    public ResponseDto<PartnerDetailDto> updatePartner(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody PartnerDetailDto partnerDetailDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDetailService.updatePartner(partnerUUID, partnerDetailDto));
    }

    @GetMapping("/partners/{partner-uuid}/details")
    public ResponseDto<PartnerDetailDto> getPartnerDetails(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDetailService.getPartnerDetails(partnerUUID));
    }

}
