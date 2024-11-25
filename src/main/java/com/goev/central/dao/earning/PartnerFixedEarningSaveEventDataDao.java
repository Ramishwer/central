package com.goev.central.dao.earning;

import com.goev.central.dao.partner.detail.PartnerDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerFixedEarningSaveEventDataDao {
    private EarningRuleDao earningRuleDao;
    private PartnerDao partnerDao;
    private Float fixedEarning;
    private DateTime start;
}
