package com.goev.central.dao.vehicle.detail;

import com.goev.central.dto.vehicle.detail.VehicleDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

import static com.goev.central.constant.ApplicationConstants.GSON;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleDao extends BaseDao {
    private String plateNumber;
    private String status;
    private String partnerDetails;
    private String vehicleTransferDetails;
    private Integer soc;
    private Integer vehicleDetailsId;

}
