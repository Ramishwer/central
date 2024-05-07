


package com.goev.central.dao.shift;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ShiftConfigurationDao extends BaseDao {
    private Integer shiftId;
    private String estimatedIn ;
    private String estimatedOut;
    private String day;
    private String minimumIn ;
    private String maximumIn ;
    private String minimumOut;
    private String maximumOut;
    private String autoOut ;
    private Integer payoutModelId ;
    private String startRules ;
    private String endRules ;
}


