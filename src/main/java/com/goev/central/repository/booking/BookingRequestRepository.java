package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingDao;

public interface BookingRequestRepository {

    BookingDao save(BookingDao booking);

    BookingDao update(BookingDao booking);

    void delete(Integer id);

    BookingDao findByUUID(String uuid);

    BookingDao findById(Integer id);

}
