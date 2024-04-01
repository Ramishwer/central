package com.goev.central.dao.vehicle.detail;

import com.goev.central.dto.user.detail.UserDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

import static com.goev.central.constant.ApplicationConstants.GSON;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleModelDao extends BaseDao {
    private Integer manufacturerId;
    private String variant;
    private String model;
    private String year;
    private String month;
    private String batteryCapacity;
    private Integer kmRange;
}
