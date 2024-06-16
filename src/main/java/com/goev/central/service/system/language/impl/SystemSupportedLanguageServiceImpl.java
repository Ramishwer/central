package com.goev.central.service.system.language.impl;

import com.goev.central.dao.system.language.SystemSupportedLanguageDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.language.SystemSupportedLanguageDto;
import com.goev.central.repository.system.language.SystemSupportedLanguageRepository;
import com.goev.central.service.system.language.SystemSupportedLanguageService;
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
public class SystemSupportedLanguageServiceImpl implements SystemSupportedLanguageService {

    private final SystemSupportedLanguageRepository systemSupportedLanguageRepository;

    @Override
    public PaginatedResponseDto<SystemSupportedLanguageDto> getSystemSupportedLanguages() {
        PaginatedResponseDto<SystemSupportedLanguageDto> result = PaginatedResponseDto.<SystemSupportedLanguageDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<SystemSupportedLanguageDao> systemSupportedLanguageDaos = systemSupportedLanguageRepository.findAllActive();
        if (CollectionUtils.isEmpty(systemSupportedLanguageDaos))
            return result;

        for (SystemSupportedLanguageDao systemSupportedLanguageDao : systemSupportedLanguageDaos) {
            result.getElements().add(SystemSupportedLanguageDto.builder()
                    .uuid(systemSupportedLanguageDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public SystemSupportedLanguageDto createSystemSupportedLanguage(SystemSupportedLanguageDto systemSupportedLanguageDto) {

        SystemSupportedLanguageDao systemSupportedLanguageDao = new SystemSupportedLanguageDao();
        systemSupportedLanguageDao = systemSupportedLanguageRepository.save(systemSupportedLanguageDao);
        if (systemSupportedLanguageDao == null)
            throw new ResponseException("Error in saving systemSupportedLanguage systemSupportedLanguage");
        return SystemSupportedLanguageDto.builder()
                .uuid(systemSupportedLanguageDao.getUuid()).build();
    }

    @Override
    public SystemSupportedLanguageDto updateSystemSupportedLanguage(String systemSupportedLanguageUUID, SystemSupportedLanguageDto systemSupportedLanguageDto) {
        SystemSupportedLanguageDao systemSupportedLanguageDao = systemSupportedLanguageRepository.findByUUID(systemSupportedLanguageUUID);
        if (systemSupportedLanguageDao == null)
            throw new ResponseException("No systemSupportedLanguage  found for Id :" + systemSupportedLanguageUUID);
        SystemSupportedLanguageDao newSystemSupportedLanguageDao = new SystemSupportedLanguageDao();


        newSystemSupportedLanguageDao.setId(systemSupportedLanguageDao.getId());
        newSystemSupportedLanguageDao.setUuid(systemSupportedLanguageDao.getUuid());
        systemSupportedLanguageDao = systemSupportedLanguageRepository.update(newSystemSupportedLanguageDao);
        if (systemSupportedLanguageDao == null)
            throw new ResponseException("Error in updating details systemSupportedLanguage ");
        return SystemSupportedLanguageDto.builder()
                .uuid(systemSupportedLanguageDao.getUuid()).build();
    }

    @Override
    public SystemSupportedLanguageDto getSystemSupportedLanguageDetails(String systemSupportedLanguageUUID) {
        SystemSupportedLanguageDao systemSupportedLanguageDao = systemSupportedLanguageRepository.findByUUID(systemSupportedLanguageUUID);
        if (systemSupportedLanguageDao == null)
            throw new ResponseException("No systemSupportedLanguage  found for Id :" + systemSupportedLanguageUUID);
        return SystemSupportedLanguageDto.builder()
                .uuid(systemSupportedLanguageDao.getUuid()).build();
    }

    @Override
    public Boolean deleteSystemSupportedLanguage(String systemSupportedLanguageUUID) {
        SystemSupportedLanguageDao systemSupportedLanguageDao = systemSupportedLanguageRepository.findByUUID(systemSupportedLanguageUUID);
        if (systemSupportedLanguageDao == null)
            throw new ResponseException("No systemSupportedLanguage  found for Id :" + systemSupportedLanguageUUID);
        systemSupportedLanguageRepository.delete(systemSupportedLanguageDao.getId());
        return true;
    }
}
