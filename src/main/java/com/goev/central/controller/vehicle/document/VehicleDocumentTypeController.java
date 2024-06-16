package com.goev.central.controller.vehicle.document;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.document.VehicleDocumentTypeDto;
import com.goev.central.service.vehicle.document.VehicleDocumentTypeService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleDocumentTypeController {


    private final VehicleDocumentTypeService vehicleDocumentTypeService;

    @GetMapping("/document-types")
    public ResponseDto<PaginatedResponseDto<VehicleDocumentTypeDto>> getCategories() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleDocumentTypeService.getDocumentTypes());
    }

    @PostMapping("/document-types")
    public ResponseDto<VehicleDocumentTypeDto> createDocumentType(@RequestBody VehicleDocumentTypeDto vehicleDocumentTypeDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleDocumentTypeService.createDocumentType(vehicleDocumentTypeDto));
    }

    @PutMapping("/document-types/{document-type-uuid}")
    public ResponseDto<VehicleDocumentTypeDto> updateDocumentType(@PathVariable(value = "document-type-uuid") String documentTypeUUID, @RequestBody VehicleDocumentTypeDto vehicleDocumentTypeDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleDocumentTypeService.updateDocumentType(documentTypeUUID, vehicleDocumentTypeDto));
    }

    @GetMapping("/document-types/{document-type-uuid}")
    public ResponseDto<VehicleDocumentTypeDto> getDocumentTypeDetails(@PathVariable(value = "document-type-uuid") String documentTypeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleDocumentTypeService.getDocumentTypeDetails(documentTypeUUID));
    }

    @DeleteMapping("/document-types/{document-type-uuid}")
    public ResponseDto<Boolean> deleteDocumentType(@PathVariable(value = "document-type-uuid") String documentTypeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleDocumentTypeService.deleteDocumentType(documentTypeUUID));
    }
}
