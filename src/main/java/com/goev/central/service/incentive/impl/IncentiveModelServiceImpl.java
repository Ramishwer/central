package com.goev.central.service.incentive.impl;

import com.goev.central.dao.incentive.IncentiveModelDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.incentive.IncentiveModelDto;
import com.goev.central.repository.incentive.IncentiveModelRepository;
import com.goev.central.service.incentive.IncentiveModelService;
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
public class IncentiveModelServiceImpl implements IncentiveModelService {

    private final IncentiveModelRepository incentiveModelRepository;

    @Override
    public PaginatedResponseDto<IncentiveModelDto> getIncentiveModels() {
        PaginatedResponseDto<IncentiveModelDto> result = PaginatedResponseDto.<IncentiveModelDto>builder().elements(new ArrayList<>()).build();
        List<IncentiveModelDao> incentiveModelDaos = incentiveModelRepository.findAllActive();
        if (CollectionUtils.isEmpty(incentiveModelDaos))
            return result;

        for (IncentiveModelDao incentiveModelDao : incentiveModelDaos) {
            result.getElements().add(IncentiveModelDto.fromDao(incentiveModelDao));
        }
        return result;
    }

    @Override
    public IncentiveModelDto createIncentiveModel(IncentiveModelDto incentiveModelDto) {

        IncentiveModelDao incentiveModelDao = new IncentiveModelDao();
        incentiveModelDao = incentiveModelRepository.save(incentiveModelDao);
        if (incentiveModelDao == null)
            throw new ResponseException("Error in saving incentiveModel incentiveModel");
        return IncentiveModelDto.builder()
                .uuid(incentiveModelDao.getUuid()).build();
    }

    @Override
    public IncentiveModelDto updateIncentiveModel(String incentiveModelUUID, IncentiveModelDto incentiveModelDto) {
        IncentiveModelDao incentiveModelDao = incentiveModelRepository.findByUUID(incentiveModelUUID);
        if (incentiveModelDao == null)
            throw new ResponseException("No incentiveModel  found for Id :" + incentiveModelUUID);
        IncentiveModelDao newIncentiveModelDao = new IncentiveModelDao();


        newIncentiveModelDao.setId(incentiveModelDao.getId());
        newIncentiveModelDao.setUuid(incentiveModelDao.getUuid());
        incentiveModelDao = incentiveModelRepository.update(newIncentiveModelDao);
        if (incentiveModelDao == null)
            throw new ResponseException("Error in updating details incentiveModel ");
        return IncentiveModelDto.builder()
                .uuid(incentiveModelDao.getUuid()).build();
    }

    @Override
    public IncentiveModelDto getIncentiveModelDetails(String incentiveModelUUID) {
        IncentiveModelDao incentiveModelDao = incentiveModelRepository.findByUUID(incentiveModelUUID);
        if (incentiveModelDao == null)
            throw new ResponseException("No incentiveModel  found for Id :" + incentiveModelUUID);
        return IncentiveModelDto.builder()
                .uuid(incentiveModelDao.getUuid()).build();
    }

    @Override
    public Boolean deleteIncentiveModel(String incentiveModelUUID) {
        IncentiveModelDao incentiveModelDao = incentiveModelRepository.findByUUID(incentiveModelUUID);
        if (incentiveModelDao == null)
            throw new ResponseException("No incentiveModel  found for Id :" + incentiveModelUUID);
        incentiveModelRepository.delete(incentiveModelDao.getId());
        return true;
    }
}
