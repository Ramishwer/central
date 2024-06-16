package com.goev.central.dto.customer.app;

import com.fasterxml.jackson.annotation.JsonInclude;
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
