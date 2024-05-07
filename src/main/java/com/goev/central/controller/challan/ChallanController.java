package com.goev.central.controller.challan;

import com.goev.central.dto.challan.ChallanDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.service.challan.ChallanService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/challan-management")
@AllArgsConstructor
public class ChallanController {

    private final ChallanService challanService;

    @GetMapping("/challans")
    public ResponseDto<PaginatedResponseDto<ChallanDto>> getChallans(@RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, challanService.getChallans());
    }

    

    @GetMapping("/challans/{challan-uuid}")
    public ResponseDto<ChallanDto> getChallanDetails(@PathVariable(value = "challan-uuid")String challanUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, challanService.getChallanDetails(challanUUID));
    }


    @PostMapping("/challans")
    public ResponseDto<ChallanDto> createChallan(@RequestBody ChallanDto challanDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, challanService.createChallan(challanDto));
    }

    @PutMapping("/challans/{challan-uuid}")
    public ResponseDto<ChallanDto> updateChallan(@PathVariable(value = "challan-uuid")String challanUUID, @RequestBody ChallanDto challanDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, challanService.updateChallan(challanUUID,challanDto));
    }
    @DeleteMapping("/challans/{challan-uuid}")
    public ResponseDto<Boolean> deleteChallan(@PathVariable(value = "challan-uuid")String challanUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, challanService.deleteChallan(challanUUID));
    }
}
