package com.goev.central.dao.vehicle.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleSegmentMappingDao extends BaseDao {
    private Integer vehicleId;
    private Integer vehicleSegmentId;
    private String status;
}
