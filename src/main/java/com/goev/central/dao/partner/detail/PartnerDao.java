package com.goev.central.dao.partner.detail;

import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

import static com.goev.central.constant.ApplicationConstants.GSON;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartnerDao extends BaseDao {
    private String punchId;
    private String authId;
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
