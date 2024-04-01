package com.goev.central.controller.partner;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerReferenceDto;
import com.goev.central.service.partner.PartnerReferenceService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management/partners")
@AllArgsConstructor
public class PartnerReferenceController {
    private final PartnerReferenceService partnerReferenceService;

    @GetMapping("{partner-uuid}/references")
    public ResponseDto<PaginatedResponseDto<PartnerReferenceDto>> getReferences(@PathVariable(value = "partner-uuid")String partnerUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerReferenceService.getReferences(partnerUUID));
    }
    @PostMapping("{partner-uuid}/references")
    public ResponseDto<PartnerReferenceDto> createReference(@PathVariable(value = "partner-uuid")String partnerUUID,@RequestBody PartnerReferenceDto partnerReferenceDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerReferenceService.createReference(partnerUUID,partnerReferenceDto));
    }

    @PutMapping("{partner-uuid}/references/{reference-uuid}")
    public ResponseDto<PartnerReferenceDto> updateReference(@PathVariable(value = "partner-uuid")String partnerUUID,@PathVariable(value = "reference-uuid")String referenceUUID, @RequestBody PartnerReferenceDto partnerReferenceDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerReferenceService.updateReference(partnerUUID,referenceUUID,partnerReferenceDto));
    }

    @GetMapping("{partner-uuid}/references/{reference-uuid}")
    public ResponseDto<PartnerReferenceDto> getReferenceDetails(@PathVariable(value = "partner-uuid")String partnerUUID,@PathVariable(value = "reference-uuid")String referenceUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerReferenceService.getReferenceDetails(partnerUUID,referenceUUID));
    }

    @DeleteMapping("{partner-uuid}/references/{reference-uuid}")
    public ResponseDto<Boolean> deleteReference(@PathVariable(value = "partner-uuid")String partnerUUID,@PathVariable(value = "reference-uuid")String referenceUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerReferenceService.deleteReference(partnerUUID,referenceUUID));
    }
}
