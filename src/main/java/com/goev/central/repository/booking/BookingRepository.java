package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingDao;
import org.joda.time.DateTime;

import java.util.List;

public interface BookingRepository {
    BookingDao save(BookingDao booking);

    BookingDao update(BookingDao booking);

    void delete(Integer id);

    BookingDao findByUUID(String uuid);

    BookingDao findById(Integer id);

    List<BookingDao> findAllByIds(List<Integer> ids);

    List<BookingDao> findAllActive(String status, String subStatus);

    List<BookingDao> findAllActiveWithTime(DateTime start, DateTime end);
}