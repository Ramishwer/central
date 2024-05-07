package com.goev.central.dao.asset;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AssetDao extends BaseDao {
    private String assetName;
    private String assetDescription;
    private String assetImageUrl;
    private String assetDetails;
    private String parentType;
    private String serialNo;
}
