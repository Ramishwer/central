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
    private Integer bookingTypeId;
    private String bookingTypeDetails;
    private String status;
    private String subStatus;
    private String startLocationDetails;
    private String endLocationDetails;
    private Integer bookingDetailsId;
    private String customerDetails;
    private String viewInfo;
    private Integer customerId;

    public static BookingScheduleDao fromDto(BookingScheduleDto bookingDto) {
        BookingScheduleDao bookingDao = new BookingScheduleDao();
        bookingDao.setUuid(bookingDto.getUuid());
        return bookingDao;
    }
}
