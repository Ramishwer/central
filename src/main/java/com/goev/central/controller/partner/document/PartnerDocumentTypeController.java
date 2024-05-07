package com.goev.central.controller.partner.document;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.document.PartnerDocumentTypeDto;
import com.goev.central.service.partner.document.PartnerDocumentTypeService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerDocumentTypeController {


    private final PartnerDocumentTypeService partnerDocumentTypeService;

    @GetMapping("/document-types")
    public ResponseDto<PaginatedResponseDto<PartnerDocumentTypeDto>> getCategories(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerDocumentTypeService.getDocumentTypes());
    }
    @PostMapping("/document-types")
    public ResponseDto<PartnerDocumentTypeDto> createDocumentType(@RequestBody PartnerDocumentTypeDto partnerDocumentTypeDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerDocumentTypeService.createDocumentType(partnerDocumentTypeDto));
    }

    @PutMapping("/document-types/{document-type-uuid}")
    public ResponseDto<PartnerDocumentTypeDto> updateDocumentType(@PathVariable(value = "document-type-uuid")String documentTypeUUID, @RequestBody PartnerDocumentTypeDto partnerDocumentTypeDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerDocumentTypeService.updateDocumentType(documentTypeUUID,partnerDocumentTypeDto));
    }

    @GetMapping("/document-types/{document-type-uuid}")
    public ResponseDto<PartnerDocumentTypeDto> getDocumentTypeDetails(@PathVariable(value = "document-type-uuid")String documentTypeUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerDocumentTypeService.getDocumentTypeDetails(documentTypeUUID));
    }

    @DeleteMapping("/document-types/{document-type-uuid}")
    public ResponseDto<Boolean> deleteDocumentType(@PathVariable(value = "document-type-uuid")String documentTypeUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerDocumentTypeService.deleteDocumentType(documentTypeUUID));
    }
}
