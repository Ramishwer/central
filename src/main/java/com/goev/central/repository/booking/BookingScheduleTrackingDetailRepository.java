package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingScheduleTrackingDetailDao;
import org.joda.time.DateTime;

import java.util.List;

public interface BookingScheduleTrackingDetailRepository {
    BookingScheduleTrackingDetailDao save(BookingScheduleTrackingDetailDao bookingScheduleTrackingDetailDao);

    BookingScheduleTrackingDetailDao update(BookingScheduleTrackingDetailDao bookingScheduleTrackingDetailDao);

    void delete(Integer id);

    BookingScheduleTrackingDetailDao findByUUID(String uuid);

    BookingScheduleTrackingDetailDao findById(Integer id);

    List<BookingScheduleTrackingDetailDao> findAllByIds(List<Integer> ids);

    List<BookingScheduleTrackingDetailDao> findAllActive(String status, String subStatus);

    BookingScheduleTrackingDetailDao findByBookingScheduleIdDayDate(Integer scheduleId, String day, DateTime date);
}