


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
public class ShiftDao extends BaseDao {
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
    private String name;
    private String description;
}


