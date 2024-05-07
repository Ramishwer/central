package com.goev.central.service.vehicle.document;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.document.VehicleDocumentTypeDto;

public interface VehicleDocumentTypeService {
    PaginatedResponseDto<VehicleDocumentTypeDto> getDocumentTypes();
    VehicleDocumentTypeDto createDocumentType(VehicleDocumentTypeDto vehicleDocumentTypeDto);
    VehicleDocumentTypeDto updateDocumentType(String documentTypeUUID, VehicleDocumentTypeDto vehicleDocumentTypeDto);
    VehicleDocumentTypeDto getDocumentTypeDetails(String documentTypeUUID);
    Boolean deleteDocumentType(String documentTypeUUID);
}
