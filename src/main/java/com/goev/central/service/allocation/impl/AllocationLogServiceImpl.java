package com.goev.central.service.allocation.impl;

import com.goev.central.dao.allocation.AllocationLogDao;
import com.goev.central.dto.allocation.AllocationLogDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.allocation.AllocationLogRepository;
import com.goev.central.service.allocation.AllocationLogService;
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
public class AllocationLogServiceImpl implements AllocationLogService {

    private final AllocationLogRepository allocationLogRepository;

    @Override
    public PaginatedResponseDto<AllocationLogDto> getAllocationLogs() {
        PaginatedResponseDto<AllocationLogDto> result = PaginatedResponseDto.<AllocationLogDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<AllocationLogDao> allocationLogDaos = allocationLogRepository.findAllActive();
        if (CollectionUtils.isEmpty(allocationLogDaos))
            return result;

        for (AllocationLogDao allocationLogDao : allocationLogDaos) {
            result.getElements().add(AllocationLogDto.fromDao(allocationLogDao));
        }
        return result;
    }


    @Override
    public AllocationLogDto getAllocationLogDetails(String allocationLogUUID) {
        AllocationLogDao allocationLogDao = allocationLogRepository.findByUUID(allocationLogUUID);
        if (allocationLogDao == null)
            throw new ResponseException("No allocation log found for Id :" + allocationLogUUID);
        return AllocationLogDto.fromDao(allocationLogDao);
    }

}
