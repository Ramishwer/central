package com.goev.central.service.engine;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.engine.EngineRuleFieldDto;

public interface EngineRuleFieldService {
    PaginatedResponseDto<EngineRuleFieldDto> getEngineRuleFields();

    EngineRuleFieldDto createEngineRuleField(EngineRuleFieldDto engineRuleFieldDto);

    EngineRuleFieldDto updateEngineRuleField(String engineRuleFieldUUID, EngineRuleFieldDto engineRuleFieldDto);

    EngineRuleFieldDto getEngineRuleFieldDetails(String engineRuleFieldUUID);

    Boolean deleteEngineRuleField(String engineRuleFieldUUID);
}
