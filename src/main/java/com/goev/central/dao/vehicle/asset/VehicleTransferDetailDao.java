package com.goev.central.dao.vehicle.asset;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleTransferDetailDao extends BaseDao {
    private Integer vehicleId;
    private String transferType;
    private String transferFrom;
    private String transferTo;
    private String status;
    private String transferDetails;
    private String transferLocationDetails;
    private Integer transferLocationId;
    private Integer odometerReading;
    private Integer socReading;
}
