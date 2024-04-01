


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
public class PartnerShiftConfigurationDao extends BaseDao {
    private String shiftStart;
    private String shiftEnd;
    private Integer partnerPayoutModelId;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
}


