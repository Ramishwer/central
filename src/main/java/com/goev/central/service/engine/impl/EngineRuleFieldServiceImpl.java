package com.goev.central.service.engine.impl;

import com.goev.central.dao.engine.EngineRuleFieldDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.engine.EngineRuleFieldDto;
import com.goev.central.repository.engine.EngineRuleFieldRepository;
import com.goev.central.service.engine.EngineRuleFieldService;
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
public class EngineRuleFieldServiceImpl implements EngineRuleFieldService {

    private final EngineRuleFieldRepository engineRuleFieldRepository;

    @Override
    public PaginatedResponseDto<EngineRuleFieldDto> getEngineRuleFields() {
        PaginatedResponseDto<EngineRuleFieldDto> result = PaginatedResponseDto.<EngineRuleFieldDto>builder().elements(new ArrayList<>()).build();
        List<EngineRuleFieldDao> engineRuleFieldDaos = engineRuleFieldRepository.findAllActive();
        if (CollectionUtils.isEmpty(engineRuleFieldDaos))
            return result;

        for (EngineRuleFieldDao engineRuleFieldDao : engineRuleFieldDaos) {
            result.getElements().add(EngineRuleFieldDto.builder()
                    .uuid(engineRuleFieldDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public EngineRuleFieldDto createEngineRuleField(EngineRuleFieldDto engineRuleFieldDto) {

        EngineRuleFieldDao engineRuleFieldDao = new EngineRuleFieldDao();
        engineRuleFieldDao = engineRuleFieldRepository.save(engineRuleFieldDao);
        if (engineRuleFieldDao == null)
            throw new ResponseException("Error in saving engineRuleField engineRuleField");
        return EngineRuleFieldDto.builder()
                .uuid(engineRuleFieldDao.getUuid()).build();
    }

    @Override
    public EngineRuleFieldDto updateEngineRuleField(String engineRuleFieldUUID, EngineRuleFieldDto engineRuleFieldDto) {
        EngineRuleFieldDao engineRuleFieldDao = engineRuleFieldRepository.findByUUID(engineRuleFieldUUID);
        if (engineRuleFieldDao == null)
            throw new ResponseException("No engineRuleField  found for Id :" + engineRuleFieldUUID);
        EngineRuleFieldDao newEngineRuleFieldDao = new EngineRuleFieldDao();


        newEngineRuleFieldDao.setId(engineRuleFieldDao.getId());
        newEngineRuleFieldDao.setUuid(engineRuleFieldDao.getUuid());
        engineRuleFieldDao = engineRuleFieldRepository.update(newEngineRuleFieldDao);
        if (engineRuleFieldDao == null)
            throw new ResponseException("Error in updating details engineRuleField ");
        return EngineRuleFieldDto.builder()
                .uuid(engineRuleFieldDao.getUuid()).build();
    }

    @Override
    public EngineRuleFieldDto getEngineRuleFieldDetails(String engineRuleFieldUUID) {
        EngineRuleFieldDao engineRuleFieldDao = engineRuleFieldRepository.findByUUID(engineRuleFieldUUID);
        if (engineRuleFieldDao == null)
            throw new ResponseException("No engineRuleField  found for Id :" + engineRuleFieldUUID);
        return EngineRuleFieldDto.builder()
                .uuid(engineRuleFieldDao.getUuid()).build();
    }

    @Override
    public Boolean deleteEngineRuleField(String engineRuleFieldUUID) {
        EngineRuleFieldDao engineRuleFieldDao = engineRuleFieldRepository.findByUUID(engineRuleFieldUUID);
        if (engineRuleFieldDao == null)
            throw new ResponseException("No engineRuleField  found for Id :" + engineRuleFieldUUID);
        engineRuleFieldRepository.delete(engineRuleFieldDao.getId());
        return true;
    }
}
