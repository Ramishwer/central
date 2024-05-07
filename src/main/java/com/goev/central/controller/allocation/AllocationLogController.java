package com.goev.central.controller.allocation;

import com.goev.central.dto.allocation.AllocationLogDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.service.allocation.AllocationLogService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/allocation-management")
@AllArgsConstructor
public class AllocationLogController {

    private final AllocationLogService allocationLogService;

    @GetMapping("/allocations/logs")
    public ResponseDto<PaginatedResponseDto<AllocationLogDto>> getAllocations(@RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, allocationLogService.getAllocationLogs());
    }

    

    @GetMapping("/allocations/logs/{allocation-log-uuid}")
    public ResponseDto<AllocationLogDto> getAllocationDetails(@PathVariable(value = "allocation-log-uuid")String allocationLogUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, allocationLogService.getAllocationLogDetails(allocationLogUUID));
    }

}
