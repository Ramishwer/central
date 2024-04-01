package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingInvoicingDetailDao;

import java.util.List;

public interface BookingInvoicingDetailRepository {
    BookingInvoicingDetailDao save(BookingInvoicingDetailDao invoice);
    BookingInvoicingDetailDao update(BookingInvoicingDetailDao invoice);
    void delete(Integer id);
    BookingInvoicingDetailDao findByUUID(String uuid);
    BookingInvoicingDetailDao findById(Integer id);
    List<BookingInvoicingDetailDao> findAllByIds(List<Integer> ids);
    List<BookingInvoicingDetailDao> findAll();
}