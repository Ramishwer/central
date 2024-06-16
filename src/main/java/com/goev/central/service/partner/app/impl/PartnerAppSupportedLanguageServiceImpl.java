package com.goev.central.service.partner.app.impl;

import com.goev.central.dao.partner.app.PartnerAppSupportedLanguageDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppSupportedLanguageDto;
import com.goev.central.repository.partner.app.PartnerAppSupportedLanguageRepository;
import com.goev.central.service.partner.app.PartnerAppSupportedLanguageService;
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
public class PartnerAppSupportedLanguageServiceImpl implements PartnerAppSupportedLanguageService {

    private final PartnerAppSupportedLanguageRepository partnerAppSupportedLanguageRepository;

    @Override
    public PaginatedResponseDto<PartnerAppSupportedLanguageDto> getPartnerAppSupportedLanguages() {
        PaginatedResponseDto<PartnerAppSupportedLanguageDto> result = PaginatedResponseDto.<PartnerAppSupportedLanguageDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PartnerAppSupportedLanguageDao> partnerAppSupportedLanguageDaos = partnerAppSupportedLanguageRepository.findAllActive();
        if (CollectionUtils.isEmpty(partnerAppSupportedLanguageDaos))
            return result;

        for (PartnerAppSupportedLanguageDao partnerAppSupportedLanguageDao : partnerAppSupportedLanguageDaos) {
            result.getElements().add(PartnerAppSupportedLanguageDto.builder()
                    .uuid(partnerAppSupportedLanguageDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerAppSupportedLanguageDto createPartnerAppSupportedLanguage(PartnerAppSupportedLanguageDto partnerAppSupportedLanguageDto) {

        PartnerAppSupportedLanguageDao partnerAppSupportedLanguageDao = new PartnerAppSupportedLanguageDao();
        partnerAppSupportedLanguageDao = partnerAppSupportedLanguageRepository.save(partnerAppSupportedLanguageDao);
        if (partnerAppSupportedLanguageDao == null)
            throw new ResponseException("Error in saving partnerAppSupportedLanguage partnerAppSupportedLanguage");
        return PartnerAppSupportedLanguageDto.builder()
                .uuid(partnerAppSupportedLanguageDao.getUuid()).build();
    }

    @Override
    public PartnerAppSupportedLanguageDto updatePartnerAppSupportedLanguage(String partnerAppSupportedLanguageUUID, PartnerAppSupportedLanguageDto partnerAppSupportedLanguageDto) {
        PartnerAppSupportedLanguageDao partnerAppSupportedLanguageDao = partnerAppSupportedLanguageRepository.findByUUID(partnerAppSupportedLanguageUUID);
        if (partnerAppSupportedLanguageDao == null)
            throw new ResponseException("No partnerAppSupportedLanguage  found for Id :" + partnerAppSupportedLanguageUUID);
        PartnerAppSupportedLanguageDao newPartnerAppSupportedLanguageDao = new PartnerAppSupportedLanguageDao();


        newPartnerAppSupportedLanguageDao.setId(partnerAppSupportedLanguageDao.getId());
        newPartnerAppSupportedLanguageDao.setUuid(partnerAppSupportedLanguageDao.getUuid());
        partnerAppSupportedLanguageDao = partnerAppSupportedLanguageRepository.update(newPartnerAppSupportedLanguageDao);
        if (partnerAppSupportedLanguageDao == null)
            throw new ResponseException("Error in updating details partnerAppSupportedLanguage ");
        return PartnerAppSupportedLanguageDto.builder()
                .uuid(partnerAppSupportedLanguageDao.getUuid()).build();
    }

    @Override
    public PartnerAppSupportedLanguageDto getPartnerAppSupportedLanguageDetails(String partnerAppSupportedLanguageUUID) {
        PartnerAppSupportedLanguageDao partnerAppSupportedLanguageDao = partnerAppSupportedLanguageRepository.findByUUID(partnerAppSupportedLanguageUUID);
        if (partnerAppSupportedLanguageDao == null)
            throw new ResponseException("No partnerAppSupportedLanguage  found for Id :" + partnerAppSupportedLanguageUUID);
        return PartnerAppSupportedLanguageDto.builder()
                .uuid(partnerAppSupportedLanguageDao.getUuid()).build();
    }

    @Override
    public Boolean deletePartnerAppSupportedLanguage(String partnerAppSupportedLanguageUUID) {
        PartnerAppSupportedLanguageDao partnerAppSupportedLanguageDao = partnerAppSupportedLanguageRepository.findByUUID(partnerAppSupportedLanguageUUID);
        if (partnerAppSupportedLanguageDao == null)
            throw new ResponseException("No partnerAppSupportedLanguage  found for Id :" + partnerAppSupportedLanguageUUID);
        partnerAppSupportedLanguageRepository.delete(partnerAppSupportedLanguageDao.getId());
        return true;
    }
}
