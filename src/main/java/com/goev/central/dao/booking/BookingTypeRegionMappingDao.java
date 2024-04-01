package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingTypeRegionMappingDao extends BaseDao {
    private Integer bookingTypeId;
    private Integer regionId;
    private String allowedOperation;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
}
