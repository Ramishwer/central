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
public class AssetDto {
    private String uuid;
    private String assetName;
    private String assetDescription;
    private String assetImageUrl;
    private String parentType;
    private String serialNo;
    private Object assetDetails;
}
