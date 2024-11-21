package com.goev.central.service.earning;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.earning.EarningRuleDto;
import org.joda.time.DateTime;


public interface EarningRuleService {

   PaginatedResponseDto<EarningRuleDto> getEarningRules(String status, DateTime from, DateTime to);

   EarningRuleDto createEarningRule(EarningRuleDto earningRuleDto);

   EarningRuleDto updateEarningRule (EarningRuleDto earningRuleDto , String earningRuleUUID);

   EarningRuleDto getEarningRuleDetails(String earningRuleUUID);

   boolean inactiveEarningRule(String earningRuleUUID);

}
