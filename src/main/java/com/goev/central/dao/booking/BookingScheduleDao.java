package com.goev.central.dao.booking;

import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.booking.BookingScheduleDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingScheduleDao extends BaseDao {
    private String status;
    private Integer customerId;
    private Integer businessSegmentId;
    private Integer businessClientId;
    private String entityType;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;

    public static BookingScheduleDao fromDto(BookingScheduleDto bookingDto) {
        BookingScheduleDao bookingDao = new BookingScheduleDao();
        bookingDao.setUuid(bookingDto.getUuid());
        return bookingDao;
    }
}
