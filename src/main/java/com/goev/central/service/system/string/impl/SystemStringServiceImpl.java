package com.goev.central.service.system.string.impl;

import com.goev.central.dao.system.string.SystemStringDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.string.SystemStringDto;
import com.goev.central.repository.system.string.SystemStringRepository;
import com.goev.central.service.system.string.SystemStringService;
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
public class SystemStringServiceImpl implements SystemStringService {

    private final SystemStringRepository systemStringRepository;

    @Override
    public PaginatedResponseDto<SystemStringDto> getSystemStrings() {
        PaginatedResponseDto<SystemStringDto> result = PaginatedResponseDto.<SystemStringDto>builder().elements(new ArrayList<>()).build();
        List<SystemStringDao> systemStringDaos = systemStringRepository.findAllActive();
        if (CollectionUtils.isEmpty(systemStringDaos))
            return result;

        for (SystemStringDao systemStringDao : systemStringDaos) {
            result.getElements().add(SystemStringDto.builder()
                    .uuid(systemStringDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public SystemStringDto createSystemString(SystemStringDto systemStringDto) {

        SystemStringDao systemStringDao = new SystemStringDao();
        systemStringDao = systemStringRepository.save(systemStringDao);
        if (systemStringDao == null)
            throw new ResponseException("Error in saving systemString systemString");
        return SystemStringDto.builder()
                .uuid(systemStringDao.getUuid()).build();
    }

    @Override
    public SystemStringDto updateSystemString(String systemStringUUID, SystemStringDto systemStringDto) {
        SystemStringDao systemStringDao = systemStringRepository.findByUUID(systemStringUUID);
        if (systemStringDao == null)
            throw new ResponseException("No systemString  found for Id :" + systemStringUUID);
        SystemStringDao newSystemStringDao = new SystemStringDao();


        newSystemStringDao.setId(systemStringDao.getId());
        newSystemStringDao.setUuid(systemStringDao.getUuid());
        systemStringDao = systemStringRepository.update(newSystemStringDao);
        if (systemStringDao == null)
            throw new ResponseException("Error in updating details systemString ");
        return SystemStringDto.builder()
                .uuid(systemStringDao.getUuid()).build();
    }

    @Override
    public SystemStringDto getSystemStringDetails(String systemStringUUID) {
        SystemStringDao systemStringDao = systemStringRepository.findByUUID(systemStringUUID);
        if (systemStringDao == null)
            throw new ResponseException("No systemString  found for Id :" + systemStringUUID);
        return SystemStringDto.builder()
                .uuid(systemStringDao.getUuid()).build();
    }

    @Override
    public Boolean deleteSystemString(String systemStringUUID) {
        SystemStringDao systemStringDao = systemStringRepository.findByUUID(systemStringUUID);
        if (systemStringDao == null)
            throw new ResponseException("No systemString  found for Id :" + systemStringUUID);
        systemStringRepository.delete(systemStringDao.getId());
        return true;
    }
}
