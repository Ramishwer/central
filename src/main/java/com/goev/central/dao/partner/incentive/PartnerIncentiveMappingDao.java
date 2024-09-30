package com.goev.central.dao.partner.incentive;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerIncentiveMappingDao extends BaseDao {
    private DateTime applicableFrom;
    private DateTime applicableTo;
    private Integer partnerId;
    private Integer incentiveModelId;
    private String triggerType;
    private String status;

}
