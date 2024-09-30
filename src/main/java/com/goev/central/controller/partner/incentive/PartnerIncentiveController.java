package com.goev.central.controller.partner.incentive;

import com.goev.central.dto.partner.incentive.PartnerIncentiveMappingDto;
import com.goev.central.service.partner.incentive.PartnerIncentiveService;
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
public class PartnerIncentiveController {

    private final PartnerIncentiveService partnerIncentiveService;


    @GetMapping("/partners/{partner-uuid}/incentive-model-mappings")
    public ResponseDto<List<PartnerIncentiveMappingDto>> getIncentiveModelMappings(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerIncentiveService.getIncentiveModelMappings(partnerUUID));
    }

    @PostMapping("/partners/{partner-uuid}/incentive-model-mappings")
    public ResponseDto<PartnerIncentiveMappingDto> createIncentiveModelMapping(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody PartnerIncentiveMappingDto partnerIncentiveMappingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerIncentiveService.createIncentiveModelMapping(partnerUUID, partnerIncentiveMappingDto));
    }

    @DeleteMapping("/partners/{partner-uuid}/incentive-model-mappings/{partner-incentive-model-mapping-uuid}")
    public ResponseDto<Boolean> deleteIncentiveModelMapping(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "partner-incentive-model-mapping-uuid") String partnerIncentiveModelMappingUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerIncentiveService.deleteIncentiveModelMapping(partnerUUID, partnerIncentiveModelMappingUUID));
    }
}
