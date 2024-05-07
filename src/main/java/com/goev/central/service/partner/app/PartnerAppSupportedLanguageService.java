package com.goev.central.service.partner.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppSupportedLanguageDto;

public interface PartnerAppSupportedLanguageService {
    PaginatedResponseDto<PartnerAppSupportedLanguageDto> getPartnerAppSupportedLanguages();

    PartnerAppSupportedLanguageDto createPartnerAppSupportedLanguage(PartnerAppSupportedLanguageDto partnerAppSupportedLanguageDto);

    PartnerAppSupportedLanguageDto updatePartnerAppSupportedLanguage(String partnerAppSupportedLanguageUUID, PartnerAppSupportedLanguageDto partnerAppSupportedLanguageDto);

    PartnerAppSupportedLanguageDto getPartnerAppSupportedLanguageDetails(String partnerAppSupportedLanguageUUID);

    Boolean deletePartnerAppSupportedLanguage(String partnerAppSupportedLanguageUUID);
}
