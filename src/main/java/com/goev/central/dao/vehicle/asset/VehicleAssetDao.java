package com.goev.central.dao.vehicle.asset;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleAssetDao extends BaseDao {
    private String assetName;
    private String assetDescription;
    private String assetImageUrl;
    private String assetDetails;
}
