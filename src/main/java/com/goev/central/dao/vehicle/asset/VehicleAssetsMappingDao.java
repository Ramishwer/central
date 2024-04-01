package com.goev.central.dao.vehicle.asset;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleAssetsMappingDao extends BaseDao {
    private Integer vehicleId;
    private Integer vehicleAssetId;
    private String status;
}
