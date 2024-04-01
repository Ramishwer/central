package com.goev.central.dao.partner.payout;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerPayoutModelDao extends BaseDao {
    private String name;
    private String description;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
}


