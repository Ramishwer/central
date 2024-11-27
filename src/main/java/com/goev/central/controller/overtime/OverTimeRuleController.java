package com.goev.central.controller.overtime;

import com.goev.central.dto.earning.EarningRuleDto;
import com.goev.central.dto.overtime.OverTimeRuleDto;
import com.goev.central.service.overtime.OverTimeRuleService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/overtime-management")
@AllArgsConstructor
public class OverTimeRuleController {
    private final OverTimeRuleService overTimeRuleService;

    @PostMapping("/overtime-rule")
    public ResponseDto<OverTimeRuleDto> createEarningRule(@RequestBody OverTimeRuleDto overTimeRuleDto) {
        return new ResponseDto<OverTimeRuleDto>(StatusDto.builder().message("SUCCESS").build(), 200, overTimeRuleService.createOverTimeRule(overTimeRuleDto));
    }
}
