package com.goev.central.service.engine.impl;

import com.goev.central.dao.engine.EngineRuleDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.engine.EngineRuleDto;
import com.goev.central.repository.engine.EngineRuleRepository;
import com.goev.central.service.engine.EngineRuleService;
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
public class EngineRuleServiceImpl implements EngineRuleService {

    private final EngineRuleRepository engineRuleRepository;

    @Override
    public PaginatedResponseDto<EngineRuleDto> getEngineRules() {
        PaginatedResponseDto<EngineRuleDto> result = PaginatedResponseDto.<EngineRuleDto>builder().elements(new ArrayList<>()).build();
        List<EngineRuleDao> engineRuleDaos = engineRuleRepository.findAllActive();
        if (CollectionUtils.isEmpty(engineRuleDaos))
            return result;

        for (EngineRuleDao engineRuleDao : engineRuleDaos) {
            result.getElements().add(EngineRuleDto.builder()
                    .uuid(engineRuleDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public EngineRuleDto createEngineRule(EngineRuleDto engineRuleDto) {

        EngineRuleDao engineRuleDao = new EngineRuleDao();
        engineRuleDao = engineRuleRepository.save(engineRuleDao);
        if (engineRuleDao == null)
            throw new ResponseException("Error in saving engineRule engineRule");
        return EngineRuleDto.builder()
                .uuid(engineRuleDao.getUuid()).build();
    }

    @Override
    public EngineRuleDto updateEngineRule(String engineRuleUUID, EngineRuleDto engineRuleDto) {
        EngineRuleDao engineRuleDao = engineRuleRepository.findByUUID(engineRuleUUID);
        if (engineRuleDao == null)
            throw new ResponseException("No engineRule  found for Id :" + engineRuleUUID);
        EngineRuleDao newEngineRuleDao = new EngineRuleDao();


        newEngineRuleDao.setId(engineRuleDao.getId());
        newEngineRuleDao.setUuid(engineRuleDao.getUuid());
        engineRuleDao = engineRuleRepository.update(newEngineRuleDao);
        if (engineRuleDao == null)
            throw new ResponseException("Error in updating details engineRule ");
        return EngineRuleDto.builder()
                .uuid(engineRuleDao.getUuid()).build();
    }

    @Override
    public EngineRuleDto getEngineRuleDetails(String engineRuleUUID) {
        EngineRuleDao engineRuleDao = engineRuleRepository.findByUUID(engineRuleUUID);
        if (engineRuleDao == null)
            throw new ResponseException("No engineRule  found for Id :" + engineRuleUUID);
        return EngineRuleDto.builder()
                .uuid(engineRuleDao.getUuid()).build();
    }

    @Override
    public Boolean deleteEngineRule(String engineRuleUUID) {
        EngineRuleDao engineRuleDao = engineRuleRepository.findByUUID(engineRuleUUID);
        if (engineRuleDao == null)
            throw new ResponseException("No engineRule  found for Id :" + engineRuleUUID);
        engineRuleRepository.delete(engineRuleDao.getId());
        return true;
    }
}
