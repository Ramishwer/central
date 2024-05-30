package com.goev.central.dto.asset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.asset.AssetDao;
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
    private AssetTypeDto type;
    private String parentType;
    private String parentName;
    private String serialNo;


    public static AssetDto fromDao(AssetDao assetDao) {
        return AssetDto.builder()
                .uuid(assetDao.getUuid())
                .assetDescription(assetDao.getAssetDescription())
                .assetName(assetDao.getAssetName())
                .assetImageUrl(assetDao.getAssetImageUrl())
                .parentType(assetDao.getParentType())
                .parentName(assetDao.getParentName())
                .serialNo(assetDao.getSerialNo())
                .build();
    }
}
