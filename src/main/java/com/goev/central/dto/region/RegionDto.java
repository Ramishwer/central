package com.goev.central.dto.region;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDto {
    private String uuid;
    private String name;
    private RegionTypeDto regionType;
    private String fileUrl;
    private String description;
}



