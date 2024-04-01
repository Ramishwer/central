package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingTypeVehicleCategoryMappingDao extends BaseDao {
    private Integer bookingTypeId;
    private Integer vehicleCategoryId;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
}
