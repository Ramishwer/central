package com.goev.central.dao.location;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LocationAssetMappingDao extends BaseDao {
    private Integer locationId;
    private Integer assetId;
    private String status;
}
