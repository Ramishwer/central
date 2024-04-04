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
public class VehicleDetailDao extends BaseDao {
    private Integer vehicleId;
    private String vinNumber;
    private String motorNumber;
    private Integer vehicleModelId;
    private DateTime registrationDate;
    private Integer vehicleLeasingAgencyId;
    private Integer vehicleFinancerId;
    private Integer vehicleCategoryId;
    private DateTime onboardingDate;
    private DateTime deboardingDate;
    private Integer homeLocationId;
    private DateTime insuranceExpiry ;
    private String insurancePolicyNumber;
}
