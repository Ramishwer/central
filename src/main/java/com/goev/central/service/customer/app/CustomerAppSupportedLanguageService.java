package com.goev.central.service.customer.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.app.CustomerAppSupportedLanguageDto;

public interface CustomerAppSupportedLanguageService {
    PaginatedResponseDto<CustomerAppSupportedLanguageDto> getCustomerAppSupportedLanguages();

    CustomerAppSupportedLanguageDto createCustomerAppSupportedLanguage(CustomerAppSupportedLanguageDto customerAppSupportedLanguageDto);

    CustomerAppSupportedLanguageDto updateCustomerAppSupportedLanguage(String customerAppSupportedLanguageUUID, CustomerAppSupportedLanguageDto customerAppSupportedLanguageDto);

    CustomerAppSupportedLanguageDto getCustomerAppSupportedLanguageDetails(String customerAppSupportedLanguageUUID);

    Boolean deleteCustomerAppSupportedLanguage(String customerAppSupportedLanguageUUID);
}
