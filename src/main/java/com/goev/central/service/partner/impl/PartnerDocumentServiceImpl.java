package com.goev.central.service.partner.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.document.PartnerDocumentDao;
import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerDetailsDto;
import com.goev.central.dto.partner.document.PartnerDocumentDto;
import com.goev.central.dto.partner.document.PartnerDocumentTypeDto;
import com.goev.central.enums.DocumentStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.document.PartnerDocumentRepository;
import com.goev.central.repository.partner.document.PartnerDocumentTypeRepository;
import com.goev.central.service.partner.PartnerDocumentService;
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
    @Override
    public PaginatedResponseDto<PartnerDocumentDto> getDocuments(String partnerUUID) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner found for id :" + partnerUUID);

        List<PartnerDocumentTypeDao> activeDocumentTypes = partnerDocumentTypeRepository.findAll();
        if (CollectionUtils.isEmpty(activeDocumentTypes))
            return PaginatedResponseDto.<PartnerDocumentDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();

        Map<Integer, PartnerDocumentTypeDao> documentTypeIdToDocumentTypeMap = activeDocumentTypes.stream()
                .collect(Collectors.toMap(PartnerDocumentTypeDao::getId, Function.identity()));
        List<Integer> activeDocumentTypeIds = activeDocumentTypes.stream().map(PartnerDocumentTypeDao::getId).toList();

        Map<Integer, PartnerDocumentDao> existingDocumentMap = partnerDocumentRepository.getExistingDocumentMap(partnerDao.getId(), activeDocumentTypeIds);
        List<PartnerDocumentDto> documentList = PartnerDetailsDto.getPartnerDocumentDtoList(documentTypeIdToDocumentTypeMap, existingDocumentMap);
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

        partnerDocumentDao.setUrl(s3.getUrlForPath(partnerDocumentDto.getUrl(),partnerDocumentTypeDao.getS3Key()));
        partnerDocumentDao.setStatus(partnerDocumentDto.getStatus());
        partnerDocumentDao.setDescription(partnerDocumentDto.getDescription());
        partnerDocumentDao.setFileName(partnerDocumentDto.getFileName());
        partnerDocumentDao.setPartnerId(partnerDao.getId());
        partnerDocumentDao.setPartnerDocumentTypeId(partnerDocumentTypeDao.getId());
        partnerDocumentDao = partnerDocumentRepository.save(partnerDocumentDao);
        if (partnerDocumentDao == null)
            throw new ResponseException("Error in saving partner document");
        return PartnerDocumentDto.builder()
                .uuid(partnerDocumentDto.getUuid())
                .type(PartnerDocumentTypeDto.builder()
                        .uuid(partnerDocumentTypeDao.getUuid())
                        .label(partnerDocumentTypeDao.getLabel())
                        .name(partnerDocumentTypeDao.getName())
                        .build())
                .fileName(partnerDocumentTypeDao.getName())
                .description(partnerDocumentDao.getDescription())
                .status(partnerDocumentDao.getStatus())
                .url(partnerDocumentDao.getUrl())
                .build();
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
        newPartnerDocumentDao.setUrl(s3.getUrlForPath(partnerDocumentDto.getUrl(),partnerDocumentTypeDao.getS3Key()));
        newPartnerDocumentDao.setStatus(DocumentStatus.UPLOADED.name());
        newPartnerDocumentDao.setPartnerId(partnerDao.getId());
        partnerDocumentRepository.delete(partnerDocumentDao.getId());
        newPartnerDocumentDao = partnerDocumentRepository.save(newPartnerDocumentDao);
        if (newPartnerDocumentDao == null)
            throw new ResponseException("Error in updating details partner manufacturer");


        return PartnerDocumentDto.builder()
                .uuid(newPartnerDocumentDao.getUuid())
                .type(PartnerDocumentTypeDto.builder()
                        .uuid(partnerDocumentTypeDao.getUuid())
                        .label(partnerDocumentTypeDao.getLabel())
                        .name(partnerDocumentTypeDao.getName())
                        .build())
                .fileName(partnerDocumentTypeDao.getName())
                .description(newPartnerDocumentDao.getDescription())
                .status(newPartnerDocumentDao.getStatus())
                .url(newPartnerDocumentDao.getUrl())
                .build();
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

        return PartnerDocumentDto.builder()
                .uuid(partnerDocumentDao.getUuid())
                .type(PartnerDocumentTypeDto.builder()
                        .uuid(partnerDocumentTypeDao.getUuid())
                        .label(partnerDocumentTypeDao.getLabel())
                        .name(partnerDocumentTypeDao.getName())
                        .build())
                .fileName(partnerDocumentTypeDao.getName())
                .description(partnerDocumentDao.getDescription())
                .status(partnerDocumentDao.getStatus())
                .url(partnerDocumentDao.getUrl())
                .build();
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
}
