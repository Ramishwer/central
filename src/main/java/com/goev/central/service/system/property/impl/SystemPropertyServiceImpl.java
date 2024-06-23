package com.goev.central.service.system.property.impl;

import com.goev.central.dao.system.property.SystemPropertyDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.property.SystemPropertyDto;
import com.goev.central.repository.system.property.SystemPropertyRepository;
import com.goev.central.service.system.property.SystemPropertyService;
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
public class SystemPropertyServiceImpl implements SystemPropertyService {

    private final SystemPropertyRepository systemPropertyRepository;

    @Override
    public PaginatedResponseDto<SystemPropertyDto> getSystemProperties() {
        PaginatedResponseDto<SystemPropertyDto> result = PaginatedResponseDto.<SystemPropertyDto>builder().elements(new ArrayList<>()).build();
        List<SystemPropertyDao> systemPropertyDaos = systemPropertyRepository.findAllActive();
        if (CollectionUtils.isEmpty(systemPropertyDaos))
            return result;

        for (SystemPropertyDao systemPropertyDao : systemPropertyDaos) {
            result.getElements().add(SystemPropertyDto.builder()
                    .uuid(systemPropertyDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public SystemPropertyDto createSystemProperty(SystemPropertyDto systemPropertyDto) {

        SystemPropertyDao systemPropertyDao = new SystemPropertyDao();
        systemPropertyDao = systemPropertyRepository.save(systemPropertyDao);
        if (systemPropertyDao == null)
            throw new ResponseException("Error in saving systemProperty systemProperty");
        return SystemPropertyDto.builder()
                .uuid(systemPropertyDao.getUuid()).build();
    }

    @Override
    public SystemPropertyDto updateSystemProperty(String systemPropertyUUID, SystemPropertyDto systemPropertyDto) {
        SystemPropertyDao systemPropertyDao = systemPropertyRepository.findByUUID(systemPropertyUUID);
        if (systemPropertyDao == null)
            throw new ResponseException("No systemProperty  found for Id :" + systemPropertyUUID);
        SystemPropertyDao newSystemPropertyDao = new SystemPropertyDao();


        newSystemPropertyDao.setId(systemPropertyDao.getId());
        newSystemPropertyDao.setUuid(systemPropertyDao.getUuid());
        systemPropertyDao = systemPropertyRepository.update(newSystemPropertyDao);
        if (systemPropertyDao == null)
            throw new ResponseException("Error in updating details systemProperty ");
        return SystemPropertyDto.builder()
                .uuid(systemPropertyDao.getUuid()).build();
    }

    @Override
    public SystemPropertyDto getSystemPropertyDetails(String systemPropertyUUID) {
        SystemPropertyDao systemPropertyDao = systemPropertyRepository.findByUUID(systemPropertyUUID);
        if (systemPropertyDao == null)
            throw new ResponseException("No systemProperty  found for Id :" + systemPropertyUUID);
        return SystemPropertyDto.builder()
                .uuid(systemPropertyDao.getUuid()).build();
    }

    @Override
    public Boolean deleteSystemProperty(String systemPropertyUUID) {
        SystemPropertyDao systemPropertyDao = systemPropertyRepository.findByUUID(systemPropertyUUID);
        if (systemPropertyDao == null)
            throw new ResponseException("No systemProperty  found for Id :" + systemPropertyUUID);
        systemPropertyRepository.delete(systemPropertyDao.getId());
        return true;
    }
}
