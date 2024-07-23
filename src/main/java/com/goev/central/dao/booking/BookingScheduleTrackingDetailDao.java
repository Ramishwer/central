package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingScheduleTrackingDetailDao extends BaseDao {
    private Integer bookingScheduleId;
    private String day;
    private DateTime bookingDate;
    private Integer bookingId;
    private String status;
}
