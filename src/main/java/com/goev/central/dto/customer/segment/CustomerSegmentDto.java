package com.goev.central.dto.customer.segment;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerSegmentDto {

    private String uuid;
    private String name;
    private String description;

}
