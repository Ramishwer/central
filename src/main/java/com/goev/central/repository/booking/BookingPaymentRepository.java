package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingPaymentDao;

import java.util.List;

public interface BookingPaymentRepository {
    BookingPaymentDao save(BookingPaymentDao payment);

    BookingPaymentDao update(BookingPaymentDao payment);

    void delete(Integer id);

    BookingPaymentDao findByUUID(String uuid);

    BookingPaymentDao findById(Integer id);

    List<BookingPaymentDao> findAllByIds(List<Integer> ids);

    List<BookingPaymentDao> findAllActive();
}