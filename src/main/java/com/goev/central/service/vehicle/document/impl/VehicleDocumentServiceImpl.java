package com.goev.central.service.vehicle.document.impl;

import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dao.vehicle.document.VehicleDocumentDao;
import com.goev.central.dao.vehicle.document.VehicleDocumentTypeDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleDetailDto;
import com.goev.central.dto.vehicle.document.VehicleDocumentDto;
import com.goev.central.dto.vehicle.document.VehicleDocumentTypeDto;
import com.goev.central.enums.DocumentStatus;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.central.repository.vehicle.document.VehicleDocumentRepository;
import com.goev.central.repository.vehicle.document.VehicleDocumentTypeRepository;
import com.goev.central.service.vehicle.document.VehicleDocumentService;
import com.goev.central.utilities.S3Utils;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleDocumentServiceImpl implements VehicleDocumentService {


    private final VehicleDocumentRepository vehicleDocumentRepository;
    private final VehicleDocumentTypeRepository vehicleDocumentTypeRepository;
    private final VehicleRepository vehicleRepository;
    private final S3Utils s3;

    @Override
    public PaginatedResponseDto<VehicleDocumentDto> getDocuments(String vehicleUUID) {
        VehicleDao vehicleDao = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicleDao == null)
            throw new ResponseException("No vehicle found for id :" + vehicleUUID);

        List<VehicleDocumentTypeDao> activeDocumentTypes = vehicleDocumentTypeRepository.findAllActive();
        if (CollectionUtils.isEmpty(activeDocumentTypes))
            return PaginatedResponseDto.<VehicleDocumentDto>builder().elements(new ArrayList<>()).build();

        Map<Integer, VehicleDocumentTypeDao> documentTypeIdToDocumentTypeMap = activeDocumentTypes.stream()
                .collect(Collectors.toMap(VehicleDocumentTypeDao::getId, Function.identity()));
        List<Integer> activeDocumentTypeIds = activeDocumentTypes.stream().map(VehicleDocumentTypeDao::getId).toList();

        Map<Integer, VehicleDocumentDao> existingDocumentMap = vehicleDocumentRepository.getExistingDocumentMap(vehicleDao.getId(), activeDocumentTypeIds);
        List<VehicleDocumentDto> documentList = VehicleDetailDto.getVehicleDocumentDtoList(documentTypeIdToDocumentTypeMap, existingDocumentMap);
        return PaginatedResponseDto.<VehicleDocumentDto>builder().elements(documentList).build();
    }

    @Override
    public VehicleDocumentDto createDocument(String vehicleUUID, VehicleDocumentDto vehicleDocumentDto) {
        VehicleDao vehicleDao = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicleDao == null)
            throw new ResponseException("No vehicle found for id :" + vehicleUUID);

        VehicleDocumentDao vehicleDocumentDao = new VehicleDocumentDao();


        if (vehicleDocumentDto.getType() == null || vehicleDocumentDto.getType().getUuid() == null)
            throw new ResponseException("Error in saving vehicle model: Invalid Manufacturer");
        VehicleDocumentTypeDao vehicleDocumentTypeDao = vehicleDocumentTypeRepository.findByUUID(vehicleDocumentDto.getType().getUuid());

        if (vehicleDocumentTypeDao == null || vehicleDocumentTypeDao.getId() == null)
            throw new ResponseException("Error in saving vehicle document: Invalid Document Type");

        vehicleDocumentDao.setUrl(s3.getUrlForPath(vehicleDocumentDto.getUrl(), vehicleDocumentTypeDao.getS3Key()+"/"+vehicleDao.getPlateNumber()));
        vehicleDocumentDao.setStatus(DocumentStatus.APPROVED.name());
        vehicleDocumentDao.setDescription(vehicleDocumentDto.getDescription());
        vehicleDocumentDao.setFileName(vehicleDocumentDto.getFileName());
        vehicleDocumentDao.setVehicleId(vehicleDao.getId());

        vehicleDocumentDao.setVehicleDocumentTypeId(vehicleDocumentTypeDao.getId());
        vehicleDocumentDao = vehicleDocumentRepository.save(vehicleDocumentDao);
        if (vehicleDocumentDao == null)
            throw new ResponseException("Error in saving vehicle document");
        return VehicleDocumentDto.fromDao(vehicleDocumentDao,VehicleDocumentTypeDto.fromDao(vehicleDocumentTypeDao));
    }

    @Override
    public VehicleDocumentDto updateDocument(String vehicleUUID, String documentUUID, VehicleDocumentDto vehicleDocumentDto) {
        VehicleDao vehicleDao = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicleDao == null)
            throw new ResponseException("No vehicle found for id :" + vehicleUUID);

        VehicleDocumentDao vehicleDocumentDao = vehicleDocumentRepository.findByUUID(documentUUID);
        if (vehicleDocumentDao == null)
            throw new ResponseException("No document found for id :" + documentUUID);

        if (vehicleDocumentDto.getType() == null || vehicleDocumentDto.getType().getUuid() == null)
            throw new ResponseException("Error in saving vehicle model: Invalid Manufacturer");
        VehicleDocumentTypeDao vehicleDocumentTypeDao = vehicleDocumentTypeRepository.findByUUID(vehicleDocumentDto.getType().getUuid());

        if (vehicleDocumentTypeDao == null || vehicleDocumentTypeDao.getId() == null)
            throw new ResponseException("Error in saving vehicle document: Invalid Document Type");

        VehicleDocumentDao newVehicleDocumentDao = new VehicleDocumentDao();
        newVehicleDocumentDao.setVehicleDocumentTypeId(vehicleDocumentTypeDao.getId());
        newVehicleDocumentDao.setFileName(vehicleDocumentDto.getFileName());
        newVehicleDocumentDao.setDescription(vehicleDocumentDto.getDescription());
        newVehicleDocumentDao.setUrl(s3.getUrlForPath(vehicleDocumentDto.getUrl(), vehicleDocumentTypeDao.getS3Key()+"/"+vehicleDao.getPlateNumber()));
        newVehicleDocumentDao.setStatus(DocumentStatus.APPROVED.name());
        newVehicleDocumentDao.setVehicleId(vehicleDao.getId());
        vehicleDocumentRepository.delete(vehicleDocumentDao.getId());
        newVehicleDocumentDao = vehicleDocumentRepository.save(newVehicleDocumentDao);
        if (newVehicleDocumentDao == null)
            throw new ResponseException("Error in updating details vehicle manufacturer");


        return VehicleDocumentDto.fromDao(vehicleDocumentDao,VehicleDocumentTypeDto.fromDao(vehicleDocumentTypeDao));
    }

    @Override
    public VehicleDocumentDto getDocumentDetails(String vehicleUUID, String documentUUID) {

        VehicleDao vehicleDao = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicleDao == null)
            throw new ResponseException("No vehicle found for id :" + vehicleUUID);

        VehicleDocumentDao vehicleDocumentDao = vehicleDocumentRepository.findByUUID(documentUUID);
        if (vehicleDocumentDao == null)
            throw new ResponseException("No document found for id :" + documentUUID);

        VehicleDocumentTypeDao vehicleDocumentTypeDao = vehicleDocumentTypeRepository.findById(vehicleDocumentDao.getVehicleDocumentTypeId());

        if (vehicleDocumentTypeDao == null || vehicleDocumentTypeDao.getId() == null)
            throw new ResponseException("Error in saving vehicle document: Invalid Document Type");

        return VehicleDocumentDto.fromDao(vehicleDocumentDao,VehicleDocumentTypeDto.fromDao(vehicleDocumentTypeDao));
    }

    @Override
    public Boolean deleteDocument(String vehicleUUID, String documentUUID) {
        VehicleDao vehicleDao = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicleDao == null)
            throw new ResponseException("No vehicle found for id :" + vehicleUUID);

        VehicleDocumentDao vehicleDocumentDao = vehicleDocumentRepository.findByUUID(documentUUID);
        if (vehicleDocumentDao == null)
            throw new ResponseException("No document found for id :" + documentUUID);

        vehicleDocumentRepository.delete(vehicleDocumentDao.getId());
        return true;
    }

    @Override
    public List<VehicleDocumentDto> bulkCreateDocument(String vehicleUUID, List<VehicleDocumentDto> vehicleDocumentDtoList) {
        VehicleDao vehicleDao = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicleDao == null)
            throw new ResponseException("No vehicle found for id :" + vehicleUUID);

        if (CollectionUtils.isEmpty(vehicleDocumentDtoList))
            throw new ResponseException("No Document present");

        List<VehicleDocumentDto> result = new ArrayList<>();
        for (VehicleDocumentDto vehicleDocumentDto : vehicleDocumentDtoList) {
            if (vehicleDocumentDto.getUrl() == null)
                continue;
            if (vehicleDocumentDto.getUuid() != null) {
                result.add(updateDocument(vehicleUUID, vehicleDocumentDto.getUuid(), vehicleDocumentDto));
            } else {
                result.add(createDocument(vehicleUUID, vehicleDocumentDto));
            }

        }
        return result;
    }


    @Override
    public DocumentStatus isAllMandatoryDocumentsUploaded(Integer vehicleId) {
        List<VehicleDocumentTypeDao> activeDocumentTypes = vehicleDocumentTypeRepository.findAllActive();
        if (CollectionUtils.isEmpty(activeDocumentTypes))
            return DocumentStatus.APPROVED;

        List<VehicleDocumentTypeDao> mandatoryDocuments = activeDocumentTypes.stream().filter(VehicleDocumentTypeDao::getIsMandatory).toList();
        Map<Integer, VehicleDocumentTypeDao> documentTypeIdToDocumentTypeMap = mandatoryDocuments.stream()
                .collect(Collectors.toMap(VehicleDocumentTypeDao::getId, Function.identity()));
        List<Integer> activeDocumentTypeIds = mandatoryDocuments.stream().map(VehicleDocumentTypeDao::getId).toList();
        Map<Integer, VehicleDocumentDao> existingDocumentMap = vehicleDocumentRepository.getExistingDocumentMap(vehicleId, activeDocumentTypeIds);

        List<VehicleDocumentDto> vehicleDocumentDtoList = VehicleDetailDto.getVehicleDocumentDtoList(documentTypeIdToDocumentTypeMap, existingDocumentMap);

        List<VehicleDocumentDto> pendingDocuments = vehicleDocumentDtoList.stream().filter(x -> DocumentStatus.PENDING.name().equals(x.getStatus())).toList();
        List<VehicleDocumentDto> uploadedDocuments = vehicleDocumentDtoList.stream().filter(x -> DocumentStatus.UPLOADED.name().equals(x.getStatus())).toList();
        List<VehicleDocumentDto> rejectedDocuments = vehicleDocumentDtoList.stream().filter(x -> DocumentStatus.REJECTED.name().equals(x.getStatus())).toList();

        if (!CollectionUtils.isEmpty(pendingDocuments))
            return DocumentStatus.PENDING;

        if (!CollectionUtils.isEmpty(rejectedDocuments))
            return DocumentStatus.REJECTED;

        if (!CollectionUtils.isEmpty(uploadedDocuments))
            return DocumentStatus.UPLOADED;

        return DocumentStatus.APPROVED;
    }

}
