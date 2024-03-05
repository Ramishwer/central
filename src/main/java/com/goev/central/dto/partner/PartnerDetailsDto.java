package com.goev.central.dto.partner;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.goev.central.dto.common.DocumentDto;
import com.goev.lib.dto.AuthorisationDetailsDto;
import com.goev.lib.utilities.DateTimeSerializer;
import lombok.*;
import org.joda.time.DateTime;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerDetailsDto {
    private PartnerDto details;
    private AuthorisationDetailsDto permissions;
    private String uuid;
    private List<DocumentDto> documents;
    @JsonSerialize(using = DateTimeSerializer.class)
    private DateTime onboardingDate;
    @JsonSerialize(using = DateTimeSerializer.class)
    private DateTime deboardingDate;
}
