package com.goev.central.dao.incentive;

import com.goev.central.dto.incentive.IncentiveModelDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class IncentiveModelDao extends BaseDao {
    private String name;
    private String description;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
    private String triggerType;
    private String status;
    private String config;
    private String rules;
}


