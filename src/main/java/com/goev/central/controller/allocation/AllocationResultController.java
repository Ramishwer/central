package com.goev.central.controller.allocation;

import com.goev.central.dto.allocation.AllocationResultDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.service.allocation.AllocationResultService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/allocation-management")
@AllArgsConstructor
public class AllocationResultController {

    private final AllocationResultService allocationResultService;

    @GetMapping("/allocations/results")
    public ResponseDto<PaginatedResponseDto<AllocationResultDto>> getAllocationResults(@RequestParam("count")Integer count,
                                                                                       @RequestParam("start")Integer start,
                                                                                       @RequestParam("from")Long from,
                                                                                       @RequestParam("to")Long to,
                                                                                       @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, allocationResultService.getAllocationResults());
    }

    

    @GetMapping("/allocations/results/{allocation-result-uuid}")
    public ResponseDto<AllocationResultDto> getAllocationResultDetails(@PathVariable(value = "allocation-result-uuid")String allocationResultUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, allocationResultService.getAllocationResultDetails(allocationResultUUID));
    }

}
