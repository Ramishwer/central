package com.goev.central.dao.partner.duty;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerDutyDao extends BaseDao {
    private Integer partnerId;
    private Integer partnerShiftId;
    private Long plannedTotalOnlineTimeInMillis;
    private Long plannedTotalPauseTimeInMillis;
    private DateTime plannedDutyStartTime;
    private DateTime plannedDutyEndTime;
    private String plannedDutyStartLocationDetails;
    private String plannedDutyEndLocationDetails;
    private DateTime actualDutyStartTime;
    private DateTime actualDutyEndTime;
    private String actualDutyStartLocationDetails;
    private String actualDutyEndLocationDetails;
    private Long actualTotalOnlineTimeInMillis;
    private Long actualTotalPauseTimeInMillis;
    private String status;

    private Integer plannedDutyStartLocationId;
    private Integer plannedDutyEndLocationId;

    private Integer plannedOnlineLocationId;
    private String plannedOnlineLocationDetails;
    private Integer actualOnlineLocationId;
    private String actualOnlineLocationDetails;

    private DateTime plannedOnlineTime;
    private DateTime actualOnlineTime;
    private String vehicles;
}


