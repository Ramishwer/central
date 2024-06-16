package com.goev.central.dto.asset;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetMappingDto {
    private String uuid;
    private String parentUUID;
    private String parentType;
    private String parentName;

}
