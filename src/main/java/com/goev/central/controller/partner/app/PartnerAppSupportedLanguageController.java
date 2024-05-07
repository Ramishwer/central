package com.goev.central.controller.partner.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppSupportedLanguageDto;
import com.goev.central.service.partner.app.PartnerAppSupportedLanguageService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerAppSupportedLanguageController {

    private final PartnerAppSupportedLanguageService partnerAppSupportedLanguageService;

    @GetMapping("/app/supported-languages")
    public ResponseDto<PaginatedResponseDto<PartnerAppSupportedLanguageDto>> getPartnerAppSupportedLanguages(@RequestParam("count")Integer count,
                                                                                                         @RequestParam("start")Integer start,
                                                                                                         @RequestParam("from")Long from,
                                                                                                         @RequestParam("to")Long to,
                                                                                                         @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAppSupportedLanguageService.getPartnerAppSupportedLanguages());
    }



    @GetMapping("/app/supported-languages/{app-supportedLanguage-uuid}")
    public ResponseDto<PartnerAppSupportedLanguageDto> getPartnerAppSupportedLanguageDetails(@PathVariable(value = "app-supportedLanguage-uuid")String appSupportedLanguageUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAppSupportedLanguageService.getPartnerAppSupportedLanguageDetails(appSupportedLanguageUUID));
    }


    @PostMapping("/app/supported-languages")
    public ResponseDto<PartnerAppSupportedLanguageDto> createPartnerAppSupportedLanguage(@RequestBody PartnerAppSupportedLanguageDto partnerAppSupportedLanguageDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAppSupportedLanguageService.createPartnerAppSupportedLanguage(partnerAppSupportedLanguageDto));
    }

    @PutMapping("/app/supported-languages/{app-supportedLanguage-uuid}")
    public ResponseDto<PartnerAppSupportedLanguageDto> updatePartnerAppSupportedLanguage(@PathVariable(value = "app-supportedLanguage-uuid")String appSupportedLanguageUUID, @RequestBody PartnerAppSupportedLanguageDto partnerAppSupportedLanguageDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAppSupportedLanguageService.updatePartnerAppSupportedLanguage(appSupportedLanguageUUID,partnerAppSupportedLanguageDto));
    }
    @DeleteMapping("/app/supported-languages/{app-supportedLanguage-uuid}")
    public ResponseDto<Boolean> deletePartnerAppSupportedLanguage(@PathVariable(value = "app-supportedLanguage-uuid")String appSupportedLanguageUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, partnerAppSupportedLanguageService.deletePartnerAppSupportedLanguage(appSupportedLanguageUUID));
    }
}
