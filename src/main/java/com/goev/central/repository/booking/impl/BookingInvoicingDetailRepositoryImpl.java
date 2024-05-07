package com.goev.central.repository.booking.impl;


import com.goev.lib.enums.RecordState;
import com.goev.central.dao.booking.BookingInvoicingDetailDao;
import com.goev.central.repository.booking.BookingInvoicingDetailRepository;
import com.goev.record.central.tables.records.BookingInvoicingDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BookingInvoicingDetails.BOOKING_INVOICING_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class BookingInvoicingDetailRepositoryImpl implements BookingInvoicingDetailRepository {

    private final DSLContext context;

    @Override
    public BookingInvoicingDetailDao save(BookingInvoicingDetailDao bookingInvoicingDetailDao) {
        BookingInvoicingDetailsRecord bookingInvoicingDetailsRecord = context.newRecord(BOOKING_INVOICING_DETAILS, bookingInvoicingDetailDao);
        bookingInvoicingDetailsRecord.store();
        bookingInvoicingDetailDao.setId(bookingInvoicingDetailsRecord.getId());
        bookingInvoicingDetailDao.setUuid(bookingInvoicingDetailsRecord.getUuid());
        return bookingInvoicingDetailDao;
    }

    @Override
    public BookingInvoicingDetailDao update(BookingInvoicingDetailDao bookingInvoicingDetailDao) {
        BookingInvoicingDetailsRecord bookingInvoicingDetailsRecord = context.newRecord(BOOKING_INVOICING_DETAILS, bookingInvoicingDetailDao);
        bookingInvoicingDetailsRecord.update();
        return bookingInvoicingDetailDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKING_INVOICING_DETAILS).set(BOOKING_INVOICING_DETAILS.STATE, RecordState.DELETED.name()).where(BOOKING_INVOICING_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public BookingInvoicingDetailDao findByUUID(String uuid) {
        return context.selectFrom(BOOKING_INVOICING_DETAILS).where(BOOKING_INVOICING_DETAILS.UUID.eq(uuid)).fetchAnyInto(BookingInvoicingDetailDao.class);
    }

    @Override
    public BookingInvoicingDetailDao findById(Integer id) {
        return context.selectFrom(BOOKING_INVOICING_DETAILS).where(BOOKING_INVOICING_DETAILS.ID.eq(id)).fetchAnyInto(BookingInvoicingDetailDao.class);
    }

    @Override
    public List<BookingInvoicingDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKING_INVOICING_DETAILS).where(BOOKING_INVOICING_DETAILS.ID.in(ids)).fetchInto(BookingInvoicingDetailDao.class);
    }

    @Override
    public List<BookingInvoicingDetailDao> findAll() {
        return context.selectFrom(BOOKING_INVOICING_DETAILS).fetchInto(BookingInvoicingDetailDao.class);
    }
}
