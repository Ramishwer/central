package com.goev.central.dao.vehicle.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleSegmentDao extends BaseDao {
    private String name;
    private String description;
}
