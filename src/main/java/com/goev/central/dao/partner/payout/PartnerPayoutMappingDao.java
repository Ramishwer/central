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
public class PartnerPayoutMappingDao extends BaseDao {
    private DateTime applicableFrom;
    private DateTime applicableTo;
    private Integer partnerId;
    private String triggerType;
    private String status;
    private String config;

}
