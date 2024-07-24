package com.goev.central.dao.booking;

import com.goev.central.dto.booking.BookingScheduleDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingScheduleConfigurationDao extends BaseDao {
    private String status;
    private Integer bookingTypeId;
    private Integer requestedVehicleCategoryId;
    private String start;
    private String end;
    private String bookingConfig;
    private String variableValues;
}
