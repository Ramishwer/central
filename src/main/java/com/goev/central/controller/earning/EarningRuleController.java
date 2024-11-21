package com.goev.central.controller.earning;


import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.earning.EarningRuleDto;
import com.goev.central.service.earning.EarningRuleService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/earning-management")
@AllArgsConstructor
public class EarningRuleController {
    private final EarningRuleService earningService;

    @PostMapping("/earning-rule")
    public ResponseDto<EarningRuleDto> createEarningRule(@RequestBody EarningRuleDto earningRuleDto) {
        return new ResponseDto<EarningRuleDto>(StatusDto.builder().message("SUCCESS").build(), 200, earningService.createEarningRule(earningRuleDto));
    }

    @PutMapping("/earning-rule/{earning-rule-uuid}")
    public ResponseDto<EarningRuleDto> updateEarningRule(@PathVariable(value = "earning-rule-uuid") String earningRuleUUID ,@RequestBody EarningRuleDto earningRuleDto) {
        return new ResponseDto<EarningRuleDto>(StatusDto.builder().message("SUCCESS").build(), 200, earningService.updateEarningRule(earningRuleDto,earningRuleUUID));
    }

    @GetMapping("/earning/rules")
    public ResponseDto<PaginatedResponseDto<EarningRuleDto>> getEarningRules(@RequestParam(value = "count", required = false) Integer count,
                                                                             @RequestParam(value = "start", required = false) Integer start,
                                                                             @RequestParam(value = "from", required = false) Long from,
                                                                             @RequestParam(value = "to", required = false) Long to,
                                                                             @RequestParam(value = "status")String Status,
                                                                             @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        DateTime fromDateTime = null;
        DateTime toDateTime = null;
        if(from!=null)
            fromDateTime = new DateTime(from);
        if(to!=null)
            toDateTime = new DateTime(to);
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, earningService.getEarningRules(Status, fromDateTime,toDateTime));
    }

    @GetMapping("/earning/rule/{earning-rule-uuid}")
    public ResponseDto<EarningRuleDto> getEarningRules(@PathVariable(value = "earning-rule-uuid") String earningRuleUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, earningService.getEarningRuleDetails(earningRuleUUID));
    }

    @DeleteMapping("/earning/rule/{earning-rule-uuid}")
    public ResponseDto<Boolean> inactiveEarningRules(@PathVariable(value = "earning-rule-uuid") String earningRuleUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, earningService.inactiveEarningRule(earningRuleUUID));
    }
}
