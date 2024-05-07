package com.goev.central.service.system.language;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.language.SystemSupportedLanguageDto;

public interface SystemSupportedLanguageService {
    PaginatedResponseDto<SystemSupportedLanguageDto> getSystemSupportedLanguages();

    SystemSupportedLanguageDto createSystemSupportedLanguage(SystemSupportedLanguageDto systemSupportedLanguageDto);

    SystemSupportedLanguageDto updateSystemSupportedLanguage(String systemSupportedLanguageUUID, SystemSupportedLanguageDto systemSupportedLanguageDto);

    SystemSupportedLanguageDto getSystemSupportedLanguageDetails(String systemSupportedLanguageUUID);

    Boolean deleteSystemSupportedLanguage(String systemSupportedLanguageUUID);
}
