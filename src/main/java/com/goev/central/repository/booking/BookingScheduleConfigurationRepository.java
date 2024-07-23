package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingScheduleConfigurationDao;
import com.goev.central.dao.booking.BookingScheduleDao;
import org.joda.time.DateTime;

import java.util.List;

public interface BookingScheduleConfigurationRepository {
    BookingScheduleConfigurationDao save(BookingScheduleConfigurationDao bookingScheduleConfigurationDao);

    BookingScheduleConfigurationDao update(BookingScheduleConfigurationDao bookingScheduleConfigurationDao);

    void delete(Integer id);

    BookingScheduleConfigurationDao findByUUID(String uuid);

    BookingScheduleConfigurationDao findById(Integer id);

    List<BookingScheduleConfigurationDao> findAllByIds(List<Integer> ids);

    List<BookingScheduleConfigurationDao> findAllActive(String status, String subStatus);

    BookingScheduleConfigurationDao findByBookingScheduleIdAndDay(Integer bookingScheduleId, String day);
}