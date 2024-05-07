package com.goev.central.controller.vehicle.document;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.document.VehicleDocumentDto;
import com.goev.central.dto.vehicle.document.VehicleDocumentDto;
import com.goev.central.service.vehicle.document.VehicleDocumentService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleDocumentController {

    private final VehicleDocumentService vehicleDocumentService;

    @GetMapping("/vehicles/{vehicle-uuid}/documents")
    public ResponseDto<PaginatedResponseDto<VehicleDocumentDto>> getDocuments(@PathVariable(value = "vehicle-uuid")String vehicleUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleDocumentService.getDocuments(vehicleUUID));
    }
    @PostMapping("/vehicles/{vehicle-uuid}/documents")
    public ResponseDto<VehicleDocumentDto> createDocument(@PathVariable(value = "vehicle-uuid")String vehicleUUID,@RequestBody VehicleDocumentDto vehicleDocumentDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleDocumentService.createDocument(vehicleUUID,vehicleDocumentDto));
    }

    @PostMapping("/vehicles/{vehicle-uuid}/documents/bulk")
    public ResponseDto<List<VehicleDocumentDto>> bulkCreateDocument(@PathVariable(value = "vehicle-uuid")String vehicleUUID, @RequestBody List<VehicleDocumentDto> vehicleDocumentDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleDocumentService.bulkCreateDocument(vehicleUUID,vehicleDocumentDto));
    }
    @PutMapping("/vehicles/{vehicle-uuid}/documents/{document-uuid}")
    public ResponseDto<VehicleDocumentDto> updateDocument(@PathVariable(value = "vehicle-uuid")String vehicleUUID,@PathVariable(value = "document-uuid")String documentUUID, @RequestBody VehicleDocumentDto vehicleDocumentDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleDocumentService.updateDocument(vehicleUUID,documentUUID,vehicleDocumentDto));
    }

    @GetMapping("/vehicles/{vehicle-uuid}/documents/{document-uuid}")
    public ResponseDto<VehicleDocumentDto> getDocumentDetails(@PathVariable(value = "vehicle-uuid")String vehicleUUID,@PathVariable(value = "document-uuid")String documentUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleDocumentService.getDocumentDetails(vehicleUUID,documentUUID));
    }

    @DeleteMapping("/vehicles/{vehicle-uuid}/documents/{document-uuid}")
    public ResponseDto<Boolean> deleteDocument(@PathVariable(value = "vehicle-uuid")String vehicleUUID,@PathVariable(value = "document-uuid")String documentUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleDocumentService.deleteDocument(vehicleUUID,documentUUID));
    }
}
