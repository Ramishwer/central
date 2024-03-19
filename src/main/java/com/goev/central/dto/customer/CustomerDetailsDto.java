package com.goev.central.dto.customer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.goev.lib.utilities.DateTimeSerializer;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerDetailsDto {
    private CustomerDto details;
    private String uuid;
}
