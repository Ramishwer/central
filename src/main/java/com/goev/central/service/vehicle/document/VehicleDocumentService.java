package com.goev.central.service.vehicle.document;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.document.VehicleDocumentDto;
import com.goev.central.enums.DocumentStatus;

import java.util.List;

public interface VehicleDocumentService {
    PaginatedResponseDto<VehicleDocumentDto> getDocuments(String vehicleUUID);

    VehicleDocumentDto createDocument(String vehicleUUID, VehicleDocumentDto vehicleDocumentDto);

    VehicleDocumentDto updateDocument(String vehicleUUID, String documentUUID, VehicleDocumentDto vehicleDocumentDto);

    VehicleDocumentDto getDocumentDetails(String vehicleUUID, String documentUUID);

    Boolean deleteDocument(String vehicleUUID, String documentUUID);

    List<VehicleDocumentDto> bulkCreateDocument(String vehicleUUID, List<VehicleDocumentDto> vehicleDocumentDto);

    DocumentStatus isAllMandatoryDocumentsUploaded(Integer vehicleId);
}
