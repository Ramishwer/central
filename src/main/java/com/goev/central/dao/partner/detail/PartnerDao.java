package com.goev.central.dao.partner.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartnerDao extends BaseDao {
    private String punchId;
    private String authUuid;
    private String phoneNumber;
    private String status;
    private Integer vehicleId;
    private String vehicleDetails;
    private Integer bookingId;
    private String bookingDetails;
    private String locationDetails;
    private String homeLocationDetails;
    private String dutyDetails;
    private Integer partnerDetailsId;
    private String profileUrl;
    private String onboardingStatus;
    private String subStatus;
    private DateTime computedAvailableTime;
    private DateTime computedShiftEndTime;
    private Integer partnerDutyId;
    private Integer partnerShiftId;
    private String locationStatus;
    private Integer locationId;
    private Integer homeLocationId;
    private String viewInfo;
    private String stats;
    private String segments;

}
