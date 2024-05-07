package com.goev.central.controller.system.string;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.string.SystemStringDto;
import com.goev.central.service.system.string.SystemStringService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/system-management")
@AllArgsConstructor
public class SystemStringController {

    private final SystemStringService systemStringService;

    @GetMapping("/systems/strings")
    public ResponseDto<PaginatedResponseDto<SystemStringDto>> getSystemStrings(@RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, systemStringService.getSystemStrings());
    }

    

    @GetMapping("/systems/strings/{system-string-uuid}")
    public ResponseDto<SystemStringDto> getSystemStringDetails(@PathVariable(value = "system-string-uuid")String appStringUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, systemStringService.getSystemStringDetails(appStringUUID));
    }


    @PostMapping("/systems/strings")
    public ResponseDto<SystemStringDto> createSystemString(@RequestBody SystemStringDto systemStringDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, systemStringService.createSystemString(systemStringDto));
    }

    @PutMapping("/systems/strings/{system-string-uuid}")
    public ResponseDto<SystemStringDto> updateSystemString(@PathVariable(value = "system-string-uuid")String appStringUUID, @RequestBody SystemStringDto systemStringDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, systemStringService.updateSystemString(appStringUUID,systemStringDto));
    }
    @DeleteMapping("/systems/strings/{system-string-uuid}")
    public ResponseDto<Boolean> deleteSystemString(@PathVariable(value = "system-string-uuid")String appStringUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, systemStringService.deleteSystemString(appStringUUID));
    }
}
