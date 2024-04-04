package com.goev.central.dao.vehicle.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleModelDao extends BaseDao {
    private Integer manufacturerId;
    private String variant;
    private String name;
    private String year;
    private String month;
    private String batteryCapacity;
    private Integer kmRange;
}
