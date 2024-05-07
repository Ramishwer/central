package com.goev.central.controller.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerAttributeDto;
import com.goev.central.service.partner.detail.PartnerAttributeService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerAttributeController {

    private final PartnerAttributeService partnerAttributeService;

    @GetMapping("/partners/{partner-uuid}/attributes")
    public ResponseDto<PaginatedResponseDto<PartnerAttributeDto>> getPartnerAttributes(
            @PathVariable(value = "partner-uuid") String partnerUUID,
            @RequestParam("count") Integer count,
            @RequestParam("start") Integer start,
            @RequestParam("from") Long from,
            @RequestParam("to") Long to,
            @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAttributeService.getPartnerAttributes(partnerUUID));
    }


    @GetMapping("/partners/{partner-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<PartnerAttributeDto> getPartnerAttributeDetails(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "attribute-uuid") String attributeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAttributeService.getPartnerAttributeDetails(partnerUUID, attributeUUID));
    }


    @PostMapping("/partners/{partner-uuid}/attributes")
    public ResponseDto<PartnerAttributeDto> createPartnerAttribute(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody PartnerAttributeDto partnerAttributeDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAttributeService.createPartnerAttribute(partnerUUID, partnerAttributeDto));
    }

    @PutMapping("/partners/{partner-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<PartnerAttributeDto> updatePartnerAttribute(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "attribute-uuid") String attributeUUID, @RequestBody PartnerAttributeDto partnerAttributeDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAttributeService.updatePartnerAttribute(partnerUUID, attributeUUID, partnerAttributeDto));
    }

    @DeleteMapping("/partners/{partner-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<Boolean> deletePartnerAttribute(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "attribute-uuid") String attributeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAttributeService.deletePartnerAttribute(partnerUUID, attributeUUID));
    }
}
