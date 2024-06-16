package com.goev.central.controller.customer.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.app.CustomerAppSupportedLanguageDto;
import com.goev.central.service.customer.app.CustomerAppSupportedLanguageService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerAppSupportedLanguageController {

    private final CustomerAppSupportedLanguageService customerAppSupportedLanguageService;

    @GetMapping("/app/supported-languages")
    public ResponseDto<PaginatedResponseDto<CustomerAppSupportedLanguageDto>> getCustomerAppSupportedLanguages(@RequestParam("count") Integer count,
                                                                                                               @RequestParam("start") Integer start,
                                                                                                               @RequestParam("from") Long from,
                                                                                                               @RequestParam("to") Long to,
                                                                                                               @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppSupportedLanguageService.getCustomerAppSupportedLanguages());
    }


    @GetMapping("/app/supported-languages/{app-supportedLanguage-uuid}")
    public ResponseDto<CustomerAppSupportedLanguageDto> getCustomerAppSupportedLanguageDetails(@PathVariable(value = "app-supportedLanguage-uuid") String appSupportedLanguageUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppSupportedLanguageService.getCustomerAppSupportedLanguageDetails(appSupportedLanguageUUID));
    }


    @PostMapping("/app/supported-languages")
    public ResponseDto<CustomerAppSupportedLanguageDto> createCustomerAppSupportedLanguage(@RequestBody CustomerAppSupportedLanguageDto customerAppSupportedLanguageDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppSupportedLanguageService.createCustomerAppSupportedLanguage(customerAppSupportedLanguageDto));
    }

    @PutMapping("/app/supported-languages/{app-supportedLanguage-uuid}")
    public ResponseDto<CustomerAppSupportedLanguageDto> updateCustomerAppSupportedLanguage(@PathVariable(value = "app-supportedLanguage-uuid") String appSupportedLanguageUUID, @RequestBody CustomerAppSupportedLanguageDto customerAppSupportedLanguageDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppSupportedLanguageService.updateCustomerAppSupportedLanguage(appSupportedLanguageUUID, customerAppSupportedLanguageDto));
    }

    @DeleteMapping("/app/supported-languages/{app-supportedLanguage-uuid}")
    public ResponseDto<Boolean> deleteCustomerAppSupportedLanguage(@PathVariable(value = "app-supportedLanguage-uuid") String appSupportedLanguageUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppSupportedLanguageService.deleteCustomerAppSupportedLanguage(appSupportedLanguageUUID));
    }
}
