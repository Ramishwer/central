package com.goev.central.dto.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDto {
    private String uuid;
    private String name;
    private String type;
    private Float latitude;
    private Float longitude;
}
