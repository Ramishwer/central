package com.goev.central.service.engine;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.engine.EngineRuleDto;

public interface EngineRuleService {
    PaginatedResponseDto<EngineRuleDto> getEngineRules();

    EngineRuleDto createEngineRule(EngineRuleDto engineRuleDto);

    EngineRuleDto updateEngineRule(String engineRuleUUID, EngineRuleDto engineRuleDto);

    EngineRuleDto getEngineRuleDetails(String engineRuleUUID);

    Boolean deleteEngineRule(String engineRuleUUID);
}
