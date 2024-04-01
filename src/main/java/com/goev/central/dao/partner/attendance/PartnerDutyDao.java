


package com.goev.central.dao.partner.attendance;

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
    private Integer partnerShiftConfigurationId;
    private DateTime plannedTotalOnlineTimeInMillis;
    private DateTime plannedTotalPauseTimeInMillis;
    private DateTime plannedDutyStartTime;
    private DateTime plannedDutyEndTime;
    private String plannedDutyStartLocationDetails;
    private String plannedDutyEndLocationDetails;
    private DateTime actualDutyStartTime;
    private DateTime actualDutyEndTime;
    private String actualDutyStartLocationDetails;
    private String actualDutyEndLocationDetails;
    private DateTime actualTotalOnlineTimeInMillis;
    private DateTime actualTotalPauseTimeInMillis;
    private String status;
}


