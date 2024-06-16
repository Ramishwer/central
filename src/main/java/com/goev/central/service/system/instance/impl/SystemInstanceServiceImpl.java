package com.goev.central.service.system.instance.impl;

import com.goev.central.dao.system.instance.SystemInstanceDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.instance.SystemInstanceDto;
import com.goev.central.repository.system.instance.SystemInstanceRepository;
import com.goev.central.service.system.instance.SystemInstanceService;
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
public class SystemInstanceServiceImpl implements SystemInstanceService {

    private final SystemInstanceRepository systemInstanceRepository;

    @Override
    public PaginatedResponseDto<SystemInstanceDto> getSystemInstances() {
        PaginatedResponseDto<SystemInstanceDto> result = PaginatedResponseDto.<SystemInstanceDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<SystemInstanceDao> systemInstanceDaos = systemInstanceRepository.findAllActive();
        if (CollectionUtils.isEmpty(systemInstanceDaos))
            return result;

        for (SystemInstanceDao systemInstanceDao : systemInstanceDaos) {
            result.getElements().add(SystemInstanceDto.builder()
                    .uuid(systemInstanceDao.getUuid())
                    .build());
        }
        return result;
    }


    @Override
    public SystemInstanceDto getSystemInstanceDetails(String systemInstanceUUID) {
        SystemInstanceDao systemInstanceDao = systemInstanceRepository.findByUUID(systemInstanceUUID);
        if (systemInstanceDao == null)
            throw new ResponseException("No allocation log found for Id :" + systemInstanceUUID);
        return SystemInstanceDto.builder()
                .uuid(systemInstanceDao.getUuid()).build();
    }

}
