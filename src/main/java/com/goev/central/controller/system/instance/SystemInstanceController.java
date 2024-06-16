package com.goev.central.controller.system.instance;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.instance.SystemInstanceDto;
import com.goev.central.service.system.instance.SystemInstanceService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/system-management")
@AllArgsConstructor
public class SystemInstanceController {

    private final SystemInstanceService systemInstanceService;

    @GetMapping("/systems/instances")
    public ResponseDto<PaginatedResponseDto<SystemInstanceDto>> getSystems(@RequestParam("count") Integer count,
                                                                           @RequestParam("start") Integer start,
                                                                           @RequestParam("from") Long from,
                                                                           @RequestParam("to") Long to,
                                                                           @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemInstanceService.getSystemInstances());
    }


    @GetMapping("/systems/instances/{system-instance-uuid}")
    public ResponseDto<SystemInstanceDto> getSystemDetails(@PathVariable(value = "system-instance-uuid") String systemInstanceUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemInstanceService.getSystemInstanceDetails(systemInstanceUUID));
    }

}
