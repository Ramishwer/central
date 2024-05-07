package com.goev.central.controller.engine;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.engine.EngineRuleDto;
import com.goev.central.service.engine.EngineRuleService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/engine-management")
@AllArgsConstructor
public class EngineRuleController {

    private final EngineRuleService engineRuleService;

    @GetMapping("/engines/rules")
    public ResponseDto<PaginatedResponseDto<EngineRuleDto>> getEngineRules(@RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, engineRuleService.getEngineRules());
    }

    

    @GetMapping("/engines/rules/{rule-uuid}")
    public ResponseDto<EngineRuleDto> getEngineRuleDetails(@PathVariable(value = "rule-uuid")String ruleUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, engineRuleService.getEngineRuleDetails(ruleUUID));
    }


    @PostMapping("/engines/rules")
    public ResponseDto<EngineRuleDto> createEngineRule(@RequestBody EngineRuleDto engineRuleDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, engineRuleService.createEngineRule(engineRuleDto));
    }

    @PutMapping("/engines/rules/{rule-uuid}")
    public ResponseDto<EngineRuleDto> updateEngineRule(@PathVariable(value = "rule-uuid")String ruleUUID, @RequestBody EngineRuleDto engineRuleDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, engineRuleService.updateEngineRule(ruleUUID,engineRuleDto));
    }
    @DeleteMapping("/engines/rules/{rule-uuid}")
    public ResponseDto<Boolean> deleteEngineRule(@PathVariable(value = "rule-uuid")String ruleUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, engineRuleService.deleteEngineRule(ruleUUID));
    }
}
