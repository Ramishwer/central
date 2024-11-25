package com.goev.central.dao.earning;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;
import org.jooq.impl.QOM;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerEarningDao extends BaseDao {
    private String earningRuleId;
    private Integer partnerId;
    private String businessType;
    private String clientName;
    private DateTime startDate;
    private DateTime endDate;
    private Float totalEarning;
    private Long totalEarningFromPlatform;
    private String transactionType;
    private String transactionStatus;
    private Integer presentDays;
    private Integer absentDays;
    private Integer totalWorkingDays;
}
