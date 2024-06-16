package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingDetailDao;

import java.util.List;

public interface BookingDetailRepository {
    BookingDetailDao save(BookingDetailDao booking);

    BookingDetailDao update(BookingDetailDao booking);

    void delete(Integer id);

    BookingDetailDao findByUUID(String uuid);

    BookingDetailDao findById(Integer id);

    List<BookingDetailDao> findAllByIds(List<Integer> ids);

    List<BookingDetailDao> findAllActive();
}