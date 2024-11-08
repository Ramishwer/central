package com.goev.central.service.earning;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.earning.EarningRuleDto;

public interface EarningRuleService {

   PaginatedResponseDto<EarningRuleDto> getEarningRules();

   EarningRuleDto createEarningRule(EarningRuleDto earningRuleDto);

   EarningRuleDto updateEarningRule (EarningRuleDto earningRuleDto , String earningRuleUUID);

   EarningRuleDto getEarningRuleDetails(String earningRuleUUID);
}
