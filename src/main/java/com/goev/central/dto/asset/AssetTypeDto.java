package com.goev.central.dto.asset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.asset.AssetTypeDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetTypeDto {
    private String uuid;
    private String name;
    private String description;
    private String parentType;

    public static AssetTypeDto fromDao(AssetTypeDao assetTypeDao) {
        return AssetTypeDto.builder()
                .parentType(assetTypeDao.getParentType())
                .description(assetTypeDao.getDescription())
                .name(assetTypeDao.getName())
                .uuid(assetTypeDao.getUuid())
                .build();
    }
}
