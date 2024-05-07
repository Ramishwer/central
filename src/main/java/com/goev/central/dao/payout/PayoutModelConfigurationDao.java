package com.goev.central.dao.payout;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PayoutModelConfigurationDao extends BaseDao {
    private Integer payoutElementId;
    private Integer payoutModelId;
    private String day;
    private String variableValues;
    private String status;
}


