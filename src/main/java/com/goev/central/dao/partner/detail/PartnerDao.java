package com.goev.central.dao.partner.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

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
    private String dutyDetails;
    private Integer partnerDetailsId;
}
