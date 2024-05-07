package com.goev.central.dto.partner.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.customer.detail.CustomerDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDto;
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
