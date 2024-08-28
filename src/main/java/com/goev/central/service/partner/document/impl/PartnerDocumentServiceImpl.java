package com.goev.central.service.partner.document.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.document.PartnerDocumentDao;
import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerDetailDto;
import com.goev.central.dto.partner.document.PartnerDocumentDto;
import com.goev.central.dto.partner.document.PartnerDocumentTypeDto;
import com.goev.central.enums.DocumentStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.document.PartnerDocumentRepository;
import com.goev.central.repository.partner.document.PartnerDocumentTypeRepository;
import com.goev.central.service.partner.document.PartnerDocumentService;
import com.goev.central.utilities.EventExecutorUtils;
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
public class PartnerDocumentServiceImpl implements PartnerDocumentService {


    private final PartnerDocumentRepository partnerDocumentRepository;
    private final PartnerDocumentTypeRepository partnerDocumentTypeRepository;
    private final PartnerRepository partnerRepository;
    private final S3Utils s3;
    private final EventExecutorUtils eventExecutor;

    @Override
    public PaginatedResponseDto<PartnerDocumentDto> getDocuments(String partnerUUID) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner found for id :" + partnerUUID);

        List<PartnerDocumentTypeDao> activeDocumentTypes = partnerDocumentTypeRepository.findAllActive();
        if (CollectionUtils.isEmpty(activeDocumentTypes))
            return PaginatedResponseDto.<PartnerDocumentDto>builder().elements(new ArrayList<>()).build();

        Map<Integer, PartnerDocumentTypeDao> documentTypeIdToDocumentTypeMap = activeDocumentTypes.stream()
                .collect(Collectors.toMap(PartnerDocumentTypeDao::getId, Function.identity()));
        List<Integer> activeDocumentTypeIds = activeDocumentTypes.stream().map(PartnerDocumentTypeDao::getId).toList();

        Map<Integer, PartnerDocumentDao> existingDocumentMap = partnerDocumentRepository.getExistingDocumentMap(partnerDao.getId(), activeDocumentTypeIds);
        List<PartnerDocumentDto> documentList = PartnerDetailDto.getPartnerDocumentDtoList(documentTypeIdToDocumentTypeMap, existingDocumentMap);
        return PaginatedResponseDto.<PartnerDocumentDto>builder().elements(documentList).build();
    }

    @Override
    public PartnerDocumentDto createDocument(String partnerUUID, PartnerDocumentDto partnerDocumentDto) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner found for id :" + partnerUUID);

        PartnerDocumentDao partnerDocumentDao = new PartnerDocumentDao();

        if (partnerDocumentDto.getType() == null || partnerDocumentDto.getType().getUuid() == null)
            throw new ResponseException("Error in saving partner model: Invalid Manufacturer");
        PartnerDocumentTypeDao partnerDocumentTypeDao = partnerDocumentTypeRepository.findByUUID(partnerDocumentDto.getType().getUuid());

        if (partnerDocumentTypeDao == null || partnerDocumentTypeDao.getId() == null)
            throw new ResponseException("Error in saving partner document: Invalid Document Type");

        partnerDocumentDao.setUrl(s3.getUrlForPath(partnerDocumentDto.getUrl(), partnerDocumentTypeDao.getS3Key()+"/"+partnerDao.getPunchId()));
        partnerDocumentDao.setStatus(DocumentStatus.APPROVED.name());
        partnerDocumentDao.setDescription(partnerDocumentDto.getDescription());
        partnerDocumentDao.setFileName(partnerDocumentDto.getFileName());
        partnerDocumentDao.setPartnerId(partnerDao.getId());
        partnerDocumentDao.setPartnerDocumentTypeId(partnerDocumentTypeDao.getId());
        partnerDocumentDao = partnerDocumentRepository.save(partnerDocumentDao);
        if (partnerDocumentDao == null)
            throw new ResponseException("Error in saving partner document");
        return PartnerDocumentDto.fromDao(partnerDocumentDao,PartnerDocumentTypeDto.fromDao(partnerDocumentTypeDao));
    }

    @Override
    public PartnerDocumentDto updateDocument(String partnerUUID, String documentUUID, PartnerDocumentDto partnerDocumentDto) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner found for id :" + partnerUUID);

        PartnerDocumentDao partnerDocumentDao = partnerDocumentRepository.findByUUID(documentUUID);
        if (partnerDocumentDao == null)
            throw new ResponseException("No document found for id :" + documentUUID);

        if (partnerDocumentDto.getType() == null || partnerDocumentDto.getType().getUuid() == null)
            throw new ResponseException("Error in saving partner model: Invalid Manufacturer");
        PartnerDocumentTypeDao partnerDocumentTypeDao = partnerDocumentTypeRepository.findByUUID(partnerDocumentDto.getType().getUuid());

        if (partnerDocumentTypeDao == null || partnerDocumentTypeDao.getId() == null)
            throw new ResponseException("Error in saving partner document: Invalid Document Type");

        PartnerDocumentDao newPartnerDocumentDao = new PartnerDocumentDao();
        newPartnerDocumentDao.setPartnerDocumentTypeId(partnerDocumentTypeDao.getId());
        newPartnerDocumentDao.setFileName(partnerDocumentDto.getFileName());
        newPartnerDocumentDao.setDescription(partnerDocumentDto.getDescription());
        newPartnerDocumentDao.setUrl(s3.getUrlForPath(partnerDocumentDto.getUrl(), partnerDocumentTypeDao.getS3Key()+"/"+partnerDao.getPunchId()));
        newPartnerDocumentDao.setStatus(DocumentStatus.APPROVED.name());
        newPartnerDocumentDao.setPartnerId(partnerDao.getId());
        partnerDocumentRepository.delete(partnerDocumentDao.getId());
        newPartnerDocumentDao = partnerDocumentRepository.save(newPartnerDocumentDao);
        if (newPartnerDocumentDao == null)
            throw new ResponseException("Error in updating details partner manufacturer");


        return PartnerDocumentDto.fromDao(partnerDocumentDao,PartnerDocumentTypeDto.fromDao(partnerDocumentTypeDao));
    }

    @Override
    public PartnerDocumentDto getDocumentDetails(String partnerUUID, String documentUUID) {

        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner found for id :" + partnerUUID);

        PartnerDocumentDao partnerDocumentDao = partnerDocumentRepository.findByUUID(documentUUID);
        if (partnerDocumentDao == null)
            throw new ResponseException("No document found for id :" + documentUUID);

        PartnerDocumentTypeDao partnerDocumentTypeDao = partnerDocumentTypeRepository.findById(partnerDocumentDao.getPartnerDocumentTypeId());

        if (partnerDocumentTypeDao == null || partnerDocumentTypeDao.getId() == null)
            throw new ResponseException("Error in saving partner document: Invalid Document Type");

        return PartnerDocumentDto.fromDao(partnerDocumentDao,PartnerDocumentTypeDto.fromDao(partnerDocumentTypeDao));
    }

    @Override
    public Boolean deleteDocument(String partnerUUID, String documentUUID) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner found for id :" + partnerUUID);

        PartnerDocumentDao partnerDocumentDao = partnerDocumentRepository.findByUUID(documentUUID);
        if (partnerDocumentDao == null)
            throw new ResponseException("No document found for id :" + documentUUID);

        partnerDocumentRepository.delete(partnerDocumentDao.getId());
        return true;
    }

    @Override
    public List<PartnerDocumentDto> bulkCreateDocument(String partnerUUID, List<PartnerDocumentDto> partnerDocumentDtoList) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner found for id :" + partnerUUID);

        if (CollectionUtils.isEmpty(partnerDocumentDtoList))
            throw new ResponseException("No Document present");

        List<PartnerDocumentDto> result = new ArrayList<>();
        for (PartnerDocumentDto partnerDocumentDto : partnerDocumentDtoList) {
            if (partnerDocumentDto.getUrl() == null)
                continue;
            if (partnerDocumentDto.getUuid() != null) {
                result.add(updateDocument(partnerUUID, partnerDocumentDto.getUuid(), partnerDocumentDto));
            } else {
                result.add(createDocument(partnerUUID, partnerDocumentDto));
            }

        }
        return result;
    }

    @Override
    public PartnerDocumentDto updateDocumentStatus(String partnerUUID, String documentUUID, DocumentStatus status) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner found for id :" + partnerUUID);

        PartnerDocumentDao partnerDocumentDao = partnerDocumentRepository.findByUUID(documentUUID);
        if (partnerDocumentDao == null)
            throw new ResponseException("No document found for id :" + documentUUID);

        if (DocumentStatus.APPROVED.equals(status)) {
            partnerDocumentDao.setStatus(DocumentStatus.APPROVED.name());
            partnerDocumentDao = partnerDocumentRepository.update(partnerDocumentDao);
            eventExecutor.fireEvent("PartnerOnboardingStatusCheckEvent", partnerDao.getUuid());
        } else if (DocumentStatus.REJECTED.equals(status)) {
            partnerDocumentDao.setStatus(DocumentStatus.REJECTED.name());
            partnerDocumentDao = partnerDocumentRepository.update(partnerDocumentDao);
            eventExecutor.fireEvent("PartnerOnboardingStatusCheckEvent", partnerDao.getUuid());
        }
        PartnerDocumentTypeDao partnerDocumentTypeDao = partnerDocumentTypeRepository.findById(partnerDocumentDao.getPartnerDocumentTypeId());

        return PartnerDocumentDto.fromDao(partnerDocumentDao,PartnerDocumentTypeDto.fromDao(partnerDocumentTypeDao));
    }


    @Override
    public DocumentStatus isAllMandatoryDocumentsUploaded(Integer partnerId) {
        List<PartnerDocumentTypeDao> activeDocumentTypes = partnerDocumentTypeRepository.findAllActive();
        if (CollectionUtils.isEmpty(activeDocumentTypes))
            return DocumentStatus.APPROVED;

        List<PartnerDocumentTypeDao> mandatoryDocuments = activeDocumentTypes.stream().filter(PartnerDocumentTypeDao::getIsMandatory).toList();
        Map<Integer, PartnerDocumentTypeDao> documentTypeIdToDocumentTypeMap = mandatoryDocuments.stream()
                .collect(Collectors.toMap(PartnerDocumentTypeDao::getId, Function.identity()));
        List<Integer> activeDocumentTypeIds = mandatoryDocuments.stream().map(PartnerDocumentTypeDao::getId).toList();
        Map<Integer, PartnerDocumentDao> existingDocumentMap = partnerDocumentRepository.getExistingDocumentMap(partnerId, activeDocumentTypeIds);

        List<PartnerDocumentDto> partnerDocumentDtoList = PartnerDetailDto.getPartnerDocumentDtoList(documentTypeIdToDocumentTypeMap, existingDocumentMap);

        List<PartnerDocumentDto> pendingDocuments = partnerDocumentDtoList.stream().filter(x -> DocumentStatus.PENDING.name().equals(x.getStatus())).toList();
        List<PartnerDocumentDto> uploadedDocuments = partnerDocumentDtoList.stream().filter(x -> DocumentStatus.UPLOADED.name().equals(x.getStatus())).toList();
        List<PartnerDocumentDto> rejectedDocuments = partnerDocumentDtoList.stream().filter(x -> DocumentStatus.REJECTED.name().equals(x.getStatus())).toList();

        if (!CollectionUtils.isEmpty(pendingDocuments))
            return DocumentStatus.PENDING;

        if (!CollectionUtils.isEmpty(rejectedDocuments))
            return DocumentStatus.REJECTED;

        if (!CollectionUtils.isEmpty(uploadedDocuments))
            return DocumentStatus.UPLOADED;

        return DocumentStatus.APPROVED;
    }

}
