package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingScheduleDao;
import org.joda.time.DateTime;

import java.util.List;

public interface BookingScheduleRepository {
    BookingScheduleDao save(BookingScheduleDao bookingSchedule);

    BookingScheduleDao update(BookingScheduleDao bookingSchedule);

    void delete(Integer id);

    BookingScheduleDao findByUUID(String uuid);

    BookingScheduleDao findById(Integer id);

    List<BookingScheduleDao> findAllByIds(List<Integer> ids);

    List<BookingScheduleDao> findAllActive(String status, String subStatus);

    List<BookingScheduleDao> findAllActiveWithTimeBetween(DateTime start);
}