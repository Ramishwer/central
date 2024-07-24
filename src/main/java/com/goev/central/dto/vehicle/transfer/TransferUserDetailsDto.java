package com.goev.central.dto.vehicle.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferUserDetailsDto {
    private String uuid;
    private String firstName;
    private String lastName;
    private String entityType;
}
