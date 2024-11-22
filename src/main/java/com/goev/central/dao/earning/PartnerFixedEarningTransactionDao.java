package com.goev.central.dao.earning;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerFixedEarningTransactionDao extends BaseDao {
    private Integer id;
    private String uuid;
    private String earningRuleId;
    private Integer partnerId;
    private String businessType;
    private String clientName;
    private DateTime transactionDate;
    private Float transactionAmount;
    private String transactionType;
    private String transactionStatus;
}
