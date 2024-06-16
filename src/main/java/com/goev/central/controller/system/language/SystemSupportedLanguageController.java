package com.goev.central.controller.system.language;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.language.SystemSupportedLanguageDto;
import com.goev.central.service.system.language.SystemSupportedLanguageService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/system-management")
@AllArgsConstructor
public class SystemSupportedLanguageController {

    private final SystemSupportedLanguageService systemSupportedLanguageService;

    @GetMapping("/systems/supported-languages")
    public ResponseDto<PaginatedResponseDto<SystemSupportedLanguageDto>> getSystemSupportedLanguages(@RequestParam("count") Integer count,
                                                                                                     @RequestParam("start") Integer start,
                                                                                                     @RequestParam("from") Long from,
                                                                                                     @RequestParam("to") Long to,
                                                                                                     @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemSupportedLanguageService.getSystemSupportedLanguages());
    }


    @GetMapping("/systems/supported-languages/{app-supportedLanguage-uuid}")
    public ResponseDto<SystemSupportedLanguageDto> getSystemSupportedLanguageDetails(@PathVariable(value = "app-supportedLanguage-uuid") String appSupportedLanguageUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemSupportedLanguageService.getSystemSupportedLanguageDetails(appSupportedLanguageUUID));
    }


    @PostMapping("/systems/supported-languages")
    public ResponseDto<SystemSupportedLanguageDto> createSystemSupportedLanguage(@RequestBody SystemSupportedLanguageDto systemSupportedLanguageDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemSupportedLanguageService.createSystemSupportedLanguage(systemSupportedLanguageDto));
    }

    @PutMapping("/systems/supported-languages/{app-supportedLanguage-uuid}")
    public ResponseDto<SystemSupportedLanguageDto> updateSystemSupportedLanguage(@PathVariable(value = "app-supportedLanguage-uuid") String appSupportedLanguageUUID, @RequestBody SystemSupportedLanguageDto systemSupportedLanguageDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemSupportedLanguageService.updateSystemSupportedLanguage(appSupportedLanguageUUID, systemSupportedLanguageDto));
    }

    @DeleteMapping("/systems/supported-languages/{app-supportedLanguage-uuid}")
    public ResponseDto<Boolean> deleteSystemSupportedLanguage(@PathVariable(value = "app-supportedLanguage-uuid") String appSupportedLanguageUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemSupportedLanguageService.deleteSystemSupportedLanguage(appSupportedLanguageUUID));
    }
}
