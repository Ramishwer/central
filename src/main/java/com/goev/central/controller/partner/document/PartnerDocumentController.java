package com.goev.central.controller.partner.document;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.document.PartnerDocumentDto;
import com.goev.central.service.partner.document.PartnerDocumentService;
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
public class PartnerDocumentController {
    private final PartnerDocumentService partnerDocumentService;

    @GetMapping("/partners/{partner-uuid}/documents")
    public ResponseDto<PaginatedResponseDto<PartnerDocumentDto>> getDocuments(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDocumentService.getDocuments(partnerUUID));
    }

    @PostMapping("/partners/{partner-uuid}/documents")
    public ResponseDto<PartnerDocumentDto> createDocument(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody PartnerDocumentDto partnerDocumentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDocumentService.createDocument(partnerUUID, partnerDocumentDto));
    }

    @PostMapping("/partners/{partner-uuid}/documents/bulk")
    public ResponseDto<List<PartnerDocumentDto>> bulkCreateDocument(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody List<PartnerDocumentDto> partnerDocumentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDocumentService.bulkCreateDocument(partnerUUID, partnerDocumentDto));
    }

    @PutMapping("/partners/{partner-uuid}/documents/{document-uuid}")
    public ResponseDto<PartnerDocumentDto> updateDocument(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "document-uuid") String documentUUID, @RequestBody PartnerDocumentDto partnerDocumentDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDocumentService.updateDocument(partnerUUID, documentUUID, partnerDocumentDto));
    }

    @GetMapping("/partners/{partner-uuid}/documents/{document-uuid}")
    public ResponseDto<PartnerDocumentDto> getDocumentDetails(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "document-uuid") String documentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDocumentService.getDocumentDetails(partnerUUID, documentUUID));
    }

    @DeleteMapping("/partners/{partner-uuid}/documents/{document-uuid}")
    public ResponseDto<Boolean> deleteDocument(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "document-uuid") String documentUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDocumentService.deleteDocument(partnerUUID, documentUUID));
    }
}
