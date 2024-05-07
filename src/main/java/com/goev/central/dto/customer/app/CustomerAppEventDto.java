package com.goev.central.dto.customer.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.customer.detail.CustomerDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerAppEventDto {
    private String uuid;
    private String eventName;
    private String eventDescription;
    private String eventData;
}
