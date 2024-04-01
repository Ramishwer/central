package com.goev.central.controller.vehicle;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.document.VehicleDocumentDto;
import com.goev.central.service.vehicle.VehicleDocumentService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management/vehicles")
@AllArgsConstructor
public class VehicleDocumentController {

    private final VehicleDocumentService vehicleDocumentService;

    @GetMapping("{vehicle-uuid}/documents")
    public ResponseDto<PaginatedResponseDto<VehicleDocumentDto>> getDocuments(@PathVariable(value = "vehicle-uuid")String vehicleUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleDocumentService.getDocuments(vehicleUUID));
    }
    @PostMapping("{vehicle-uuid}/documents")
    public ResponseDto<VehicleDocumentDto> createDocument(@PathVariable(value = "vehicle-uuid")String vehicleUUID,@RequestBody VehicleDocumentDto vehicleDocumentDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleDocumentService.createDocument(vehicleUUID,vehicleDocumentDto));
    }

    @PutMapping("{vehicle-uuid}/documents/{document-uuid}")
    public ResponseDto<VehicleDocumentDto> updateDocument(@PathVariable(value = "vehicle-uuid")String vehicleUUID,@PathVariable(value = "document-uuid")String documentUUID, @RequestBody VehicleDocumentDto vehicleDocumentDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleDocumentService.updateDocument(vehicleUUID,documentUUID,vehicleDocumentDto));
    }

    @GetMapping("{vehicle-uuid}/documents/{document-uuid}")
    public ResponseDto<VehicleDocumentDto> getDocumentDetails(@PathVariable(value = "vehicle-uuid")String vehicleUUID,@PathVariable(value = "document-uuid")String documentUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleDocumentService.getDocumentDetails(vehicleUUID,documentUUID));
    }

    @DeleteMapping("{vehicle-uuid}/documents/{document-uuid}")
    public ResponseDto<Boolean> deleteDocument(@PathVariable(value = "vehicle-uuid")String vehicleUUID,@PathVariable(value = "document-uuid")String documentUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleDocumentService.deleteDocument(vehicleUUID,documentUUID));
    }
}
