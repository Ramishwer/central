package com.goev.central.service.vehicle;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.document.VehicleDocumentDto;

public interface VehicleDocumentService {
    PaginatedResponseDto<VehicleDocumentDto> getDocuments(String vehicleUUID);
    VehicleDocumentDto createDocument(String vehicleUUID,VehicleDocumentDto vehicleDocumentDto);
    VehicleDocumentDto updateDocument(String vehicleUUID,String documentUUID, VehicleDocumentDto vehicleDocumentDto);
    VehicleDocumentDto getDocumentDetails(String vehicleUUID,String documentUUID);
    Boolean deleteDocument(String vehicleUUID,String documentUUID);

}
