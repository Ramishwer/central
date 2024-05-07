package com.goev.central.controller.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerReferenceDto;
import com.goev.central.service.partner.detail.PartnerReferenceService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerReferenceController {
    private final PartnerReferenceService partnerReferenceService;

    @GetMapping("/partners/{partner-uuid}/references")
    public ResponseDto<PaginatedResponseDto<PartnerReferenceDto>> getReferences(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerReferenceService.getReferences(partnerUUID));
    }

    @PostMapping("/partners/{partner-uuid}/references")
    public ResponseDto<PartnerReferenceDto> createReference(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody PartnerReferenceDto partnerReferenceDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerReferenceService.createReference(partnerUUID, partnerReferenceDto));
    }

    @PostMapping("/partners/{partner-uuid}/references/bulk")
    public ResponseDto<List<PartnerReferenceDto>> bulkCreateReference(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody List<PartnerReferenceDto> partnerReferenceDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerReferenceService.bulkCreateReference(partnerUUID, partnerReferenceDto));
    }

    @PutMapping("/partners/{partner-uuid}/references/{reference-uuid}")
    public ResponseDto<PartnerReferenceDto> updateReference(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "reference-uuid") String referenceUUID, @RequestBody PartnerReferenceDto partnerReferenceDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerReferenceService.updateReference(partnerUUID, referenceUUID, partnerReferenceDto));
    }

    @GetMapping("/partners/{partner-uuid}/references/{reference-uuid}")
    public ResponseDto<PartnerReferenceDto> getReferenceDetails(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "reference-uuid") String referenceUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerReferenceService.getReferenceDetails(partnerUUID, referenceUUID));
    }

    @DeleteMapping("/partners/{partner-uuid}/references/{reference-uuid}")
    public ResponseDto<Boolean> deleteReference(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "reference-uuid") String referenceUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerReferenceService.deleteReference(partnerUUID, referenceUUID));
    }
}
