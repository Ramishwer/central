package com.goev.central.dao.overtime;
import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OverTimeRuleDao extends BaseDao {

    private String ruleId;
    private String city;
    private String businessType;
    private String clientName;
    private Integer overtimeAmount;
    private String checks;
    private Integer checkValue;
    private Long validTill;
    private String status;

}
