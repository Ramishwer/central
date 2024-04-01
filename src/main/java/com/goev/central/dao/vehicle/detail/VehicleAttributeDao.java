package com.goev.central.dao.vehicle.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleAttributeDao extends BaseDao {
    private Integer vehicleId;
    private String attributeKey;
    private String attributeValue;
    private String name;
}
