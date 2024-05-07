package com.goev.central.dto.partner.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerAppSupportedLanguageDto {
    private String uuid;
    private String languageCode;
    private String name;
    private String s3Key;
}
