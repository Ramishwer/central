package com.goev.central.service.partner.impl;

import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.document.PartnerDocumentTypeDto;
import com.goev.central.repository.partner.document.PartnerDocumentTypeRepository;
import com.goev.central.service.partner.PartnerDocumentTypeService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerDocumentTypeServiceImpl implements PartnerDocumentTypeService {
   
    private final PartnerDocumentTypeRepository partnerDocumentTypeRepository;

    @Override
    public PaginatedResponseDto<PartnerDocumentTypeDto> getDocumentTypes() {
        PaginatedResponseDto<PartnerDocumentTypeDto> result = PaginatedResponseDto.<PartnerDocumentTypeDto>builder().totalPages(0).currentPage(0).elements(new ArrayList<>()).build();
        List<PartnerDocumentTypeDao> partnerDocumentTypeDaos = partnerDocumentTypeRepository.findAll();
        if (CollectionUtils.isEmpty(partnerDocumentTypeDaos))
            return result;

        for (PartnerDocumentTypeDao partnerDocumentTypeDao : partnerDocumentTypeDaos) {
            result.getElements().add(PartnerDocumentTypeDto.builder()
                    .name(partnerDocumentTypeDao.getName())
                    .label(partnerDocumentTypeDao.getLabel())
                    .uuid(partnerDocumentTypeDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerDocumentTypeDto createDocumentType(PartnerDocumentTypeDto partnerDocumentTypeDto) {

        PartnerDocumentTypeDao partnerDocumentTypeDao = new PartnerDocumentTypeDao();
        partnerDocumentTypeDao.setName(partnerDocumentTypeDto.getName());
        partnerDocumentTypeDao = partnerDocumentTypeRepository.save(partnerDocumentTypeDao);
        if (partnerDocumentTypeDao == null)
            throw new ResponseException("Error in saving partner documentType");
        return PartnerDocumentTypeDto.builder().name(partnerDocumentTypeDao.getName()).uuid(partnerDocumentTypeDao.getUuid()).build();
    }

    @Override
    public PartnerDocumentTypeDto updateDocumentType(String documentTypeUUID, PartnerDocumentTypeDto partnerDocumentTypeDto) {
        PartnerDocumentTypeDao partnerDocumentTypeDao = partnerDocumentTypeRepository.findByUUID(documentTypeUUID);
        if (partnerDocumentTypeDao == null)
            throw new ResponseException("No partner documentType found for Id :" + documentTypeUUID);
        PartnerDocumentTypeDao newPartnerDocumentTypeDao = new PartnerDocumentTypeDao();
        newPartnerDocumentTypeDao.setName(partnerDocumentTypeDto.getName());

        newPartnerDocumentTypeDao.setId(partnerDocumentTypeDao.getId());
        newPartnerDocumentTypeDao.setUuid(partnerDocumentTypeDao.getUuid());
        partnerDocumentTypeDao = partnerDocumentTypeRepository.update(newPartnerDocumentTypeDao);
        if (partnerDocumentTypeDao == null)
            throw new ResponseException("Error in updating details partner documentType");
        return PartnerDocumentTypeDto.builder()
                .name(partnerDocumentTypeDao.getName())
                .uuid(partnerDocumentTypeDao.getUuid()).build();
    }

    @Override
    public PartnerDocumentTypeDto getDocumentTypeDetails(String documentTypeUUID) {
        PartnerDocumentTypeDao partnerDocumentTypeDao = partnerDocumentTypeRepository.findByUUID(documentTypeUUID);
        if (partnerDocumentTypeDao == null)
            throw new ResponseException("No partner documentType found for Id :" + documentTypeUUID);
        return PartnerDocumentTypeDto.builder()
                .name(partnerDocumentTypeDao.getName())
                .uuid(partnerDocumentTypeDao.getUuid()).build();
    }

    @Override
    public Boolean deleteDocumentType(String documentTypeUUID) {
        PartnerDocumentTypeDao partnerDocumentTypeDao = partnerDocumentTypeRepository.findByUUID(documentTypeUUID);
        if (partnerDocumentTypeDao == null)
            throw new ResponseException("No partner documentType found for Id :" + documentTypeUUID);
        partnerDocumentTypeRepository.delete(partnerDocumentTypeDao.getId());
        return true;
    }
}
