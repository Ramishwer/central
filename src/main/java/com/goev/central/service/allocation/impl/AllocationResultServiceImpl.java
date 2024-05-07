package com.goev.central.service.allocation.impl;

import com.goev.central.dao.allocation.AllocationResultDao;
import com.goev.central.dto.allocation.AllocationResultDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.allocation.AllocationResultRepository;
import com.goev.central.service.allocation.AllocationResultService;
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
public class AllocationResultServiceImpl implements AllocationResultService {

    private final AllocationResultRepository allocationResultRepository;

    @Override
    public PaginatedResponseDto<AllocationResultDto> getAllocationResults() {
        PaginatedResponseDto<AllocationResultDto> result = PaginatedResponseDto.<AllocationResultDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<AllocationResultDao> allocationResultDaos = allocationResultRepository.findAll();
        if (CollectionUtils.isEmpty(allocationResultDaos))
            return result;

        for (AllocationResultDao allocationResultDao : allocationResultDaos) {
            result.getElements().add(AllocationResultDto.builder()
                    .uuid(allocationResultDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public AllocationResultDto getAllocationResultDetails(String allocationResultUUID) {
        AllocationResultDao allocationResultDao = allocationResultRepository.findByUUID(allocationResultUUID);
        if (allocationResultDao == null)
            throw new ResponseException("No allocation result found for Id :" + allocationResultUUID);
        return AllocationResultDto.builder()
                .uuid(allocationResultDao.getUuid()).build();
    }
}
