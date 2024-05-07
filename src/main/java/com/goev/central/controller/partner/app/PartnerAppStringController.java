package com.goev.central.controller.partner.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppStringDto;
import com.goev.central.service.partner.app.PartnerAppStringService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerAppStringController {

    private final PartnerAppStringService partnerAppStringService;

    @GetMapping("/app/strings")
    public ResponseDto<PaginatedResponseDto<PartnerAppStringDto>> getPartnerAppStrings(@RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAppStringService.getPartnerAppStrings());
    }

    

    @GetMapping("/app/strings/{app-string-uuid}")
    public ResponseDto<PartnerAppStringDto> getPartnerAppStringDetails(@PathVariable(value = "app-string-uuid")String appStringUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAppStringService.getPartnerAppStringDetails(appStringUUID));
    }


    @PostMapping("/app/strings")
    public ResponseDto<PartnerAppStringDto> createPartnerAppString(@RequestBody PartnerAppStringDto partnerAppStringDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAppStringService.createPartnerAppString(partnerAppStringDto));
    }

    @PutMapping("/app/strings/{app-string-uuid}")
    public ResponseDto<PartnerAppStringDto> updatePartnerAppString(@PathVariable(value = "app-string-uuid")String appStringUUID, @RequestBody PartnerAppStringDto partnerAppStringDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAppStringService.updatePartnerAppString(appStringUUID,partnerAppStringDto));
    }
    @DeleteMapping("/app/strings/{app-string-uuid}")
    public ResponseDto<Boolean> deletePartnerAppString(@PathVariable(value = "app-string-uuid")String appStringUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAppStringService.deletePartnerAppString(appStringUUID));
    }
}
