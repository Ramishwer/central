package com.goev.central.controller.engine;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.engine.EngineRuleFieldDto;
import com.goev.central.service.engine.EngineRuleFieldService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/engine-management")
@AllArgsConstructor
public class EngineRuleFieldController {

    private final EngineRuleFieldService engineRuleFieldService;

    @GetMapping("/engines/rule-fields")
    public ResponseDto<PaginatedResponseDto<EngineRuleFieldDto>> getEngineRuleFields(@RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, engineRuleFieldService.getEngineRuleFields());
    }

    

    @GetMapping("/engines/rule-fields/{rule-field-uuid}")
    public ResponseDto<EngineRuleFieldDto> getEngineRuleFieldDetails(@PathVariable(value = "rule-field-uuid")String ruleFieldUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, engineRuleFieldService.getEngineRuleFieldDetails(ruleFieldUUID));
    }


    @PostMapping("/engines/rule-fields")
    public ResponseDto<EngineRuleFieldDto> createEngineRuleField(@RequestBody EngineRuleFieldDto engineRuleFieldDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, engineRuleFieldService.createEngineRuleField(engineRuleFieldDto));
    }

    @PutMapping("/engines/rule-fields/{rule-field-uuid}")
    public ResponseDto<EngineRuleFieldDto> updateEngineRuleField(@PathVariable(value = "rule-field-uuid")String ruleFieldUUID, @RequestBody EngineRuleFieldDto engineRuleFieldDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, engineRuleFieldService.updateEngineRuleField(ruleFieldUUID,engineRuleFieldDto));
    }
    @DeleteMapping("/engines/rule-fields/{rule-field-uuid}")
    public ResponseDto<Boolean> deleteEngineRuleField(@PathVariable(value = "rule-field-uuid")String ruleFieldUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, engineRuleFieldService.deleteEngineRuleField(ruleFieldUUID));
    }
}
