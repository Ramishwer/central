package com.goev.central.controller.system.log;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.log.SystemLogDto;
import com.goev.central.service.system.log.SystemLogService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/system-management")
@AllArgsConstructor
public class SystemLogController {

    private final SystemLogService systemLogService;

    @GetMapping("/systems/logs")
    public ResponseDto<PaginatedResponseDto<SystemLogDto>> getSystems(@RequestParam("count") Integer count,
                                                                      @RequestParam("start") Integer start,
                                                                      @RequestParam("from") Long from,
                                                                      @RequestParam("to") Long to,
                                                                      @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemLogService.getSystemLogs());
    }


    @GetMapping("/systems/logs/{system-log-uuid}")
    public ResponseDto<SystemLogDto> getSystemDetails(@PathVariable(value = "system-log-uuid") String systemLogUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemLogService.getSystemLogDetails(systemLogUUID));
    }

}
