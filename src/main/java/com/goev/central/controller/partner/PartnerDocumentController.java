package com.goev.central.controller.partner;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.document.PartnerDocumentDto;
import com.goev.central.service.partner.PartnerDocumentService;
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
public class PartnerDocumentController {
    private final PartnerDocumentService partnerDocumentService;

    @GetMapping("{partner-uuid}/documents")
    public ResponseDto<PaginatedResponseDto<PartnerDocumentDto>> getDocuments(@PathVariable(value = "partner-uuid")String partnerUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerDocumentService.getDocuments(partnerUUID));
    }
    @PostMapping("{partner-uuid}/documents")
    public ResponseDto<PartnerDocumentDto> createDocument(@PathVariable(value = "partner-uuid")String partnerUUID,@RequestBody PartnerDocumentDto partnerDocumentDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerDocumentService.createDocument(partnerUUID,partnerDocumentDto));
    }

    @PutMapping("{partner-uuid}/documents/{document-uuid}")
    public ResponseDto<PartnerDocumentDto> updateDocument(@PathVariable(value = "partner-uuid")String partnerUUID,@PathVariable(value = "document-uuid")String documentUUID, @RequestBody PartnerDocumentDto partnerDocumentDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerDocumentService.updateDocument(partnerUUID,documentUUID,partnerDocumentDto));
    }

    @GetMapping("{partner-uuid}/documents/{document-uuid}")
    public ResponseDto<PartnerDocumentDto> getDocumentDetails(@PathVariable(value = "partner-uuid")String partnerUUID,@PathVariable(value = "document-uuid")String documentUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerDocumentService.getDocumentDetails(partnerUUID,documentUUID));
    }

    @DeleteMapping("{partner-uuid}/documents/{document-uuid}")
    public ResponseDto<Boolean> deleteDocument(@PathVariable(value = "partner-uuid")String partnerUUID,@PathVariable(value = "document-uuid")String documentUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerDocumentService.deleteDocument(partnerUUID,documentUUID));
    }
}
