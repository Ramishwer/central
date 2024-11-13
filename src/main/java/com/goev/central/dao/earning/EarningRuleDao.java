package com.goev.central.dao.earning;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EarningRuleDao extends BaseDao {
    private String ruleId;
    private String city;
    private String businessType;
    private String clientName;
    private Integer fixedIncome;
    private Integer variableIncome;
    private String checks;
    private Integer checkValue;
    private Long validTill;
    public void getUuid(String uuid) {
    }
}
