package com.goev.central.service.system.log.impl;

import com.goev.central.dao.system.log.SystemLogDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.log.SystemLogDto;
import com.goev.central.repository.system.log.SystemLogRepository;
import com.goev.central.service.system.log.SystemLogService;
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
public class SystemLogServiceImpl implements SystemLogService {

    private final SystemLogRepository systemLogRepository;

    @Override
    public PaginatedResponseDto<SystemLogDto> getSystemLogs() {
        PaginatedResponseDto<SystemLogDto> result = PaginatedResponseDto.<SystemLogDto>builder().elements(new ArrayList<>()).build();
        List<SystemLogDao> systemLogDaos = systemLogRepository.findAllActive();
        if (CollectionUtils.isEmpty(systemLogDaos))
            return result;

        for (SystemLogDao systemLogDao : systemLogDaos) {
            result.getElements().add(SystemLogDto.builder()
                    .uuid(systemLogDao.getUuid())
                    .build());
        }
        return result;
    }


    @Override
    public SystemLogDto getSystemLogDetails(String systemLogUUID) {
        SystemLogDao systemLogDao = systemLogRepository.findByUUID(systemLogUUID);
        if (systemLogDao == null)
            throw new ResponseException("No allocation log found for Id :" + systemLogUUID);
        return SystemLogDto.builder()
                .uuid(systemLogDao.getUuid()).build();
    }

}
