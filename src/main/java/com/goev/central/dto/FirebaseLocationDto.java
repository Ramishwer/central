package com.goev.central.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.lib.dto.LatLongDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FirebaseLocationDto {
    private Long timestamp;
    private Boolean mocked;
    private LatLongDto coords;
}
