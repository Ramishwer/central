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
public class PartnerAppEventDto {
    private String uuid;
    private String eventName;
    private String eventDescription;
    private String eventData;
}
