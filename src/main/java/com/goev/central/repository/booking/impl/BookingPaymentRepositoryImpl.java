package com.goev.central.repository.booking.impl;


import com.goev.central.dao.booking.BookingPaymentDao;
import com.goev.central.repository.booking.BookingPaymentRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BookingPaymentsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BookingPayments.BOOKING_PAYMENTS;

@Slf4j
@Repository
@AllArgsConstructor
public class BookingPaymentRepositoryImpl implements BookingPaymentRepository {

    private final DSLContext context;

    @Override
    public BookingPaymentDao save(BookingPaymentDao bookingPaymentDao) {
        BookingPaymentsRecord bookingPaymentsRecord = context.newRecord(BOOKING_PAYMENTS, bookingPaymentDao);
        bookingPaymentsRecord.store();
        bookingPaymentDao.setId(bookingPaymentsRecord.getId());
        bookingPaymentDao.setUuid(bookingPaymentsRecord.getUuid());
        bookingPaymentDao.setCreatedBy(bookingPaymentsRecord.getCreatedBy());
        bookingPaymentDao.setUpdatedBy(bookingPaymentsRecord.getUpdatedBy());
        bookingPaymentDao.setCreatedOn(bookingPaymentsRecord.getCreatedOn());
        bookingPaymentDao.setUpdatedOn(bookingPaymentsRecord.getUpdatedOn());
        bookingPaymentDao.setIsActive(bookingPaymentsRecord.getIsActive());
        bookingPaymentDao.setState(bookingPaymentsRecord.getState());
        bookingPaymentDao.setApiSource(bookingPaymentsRecord.getApiSource());
        bookingPaymentDao.setNotes(bookingPaymentsRecord.getNotes());
        return bookingPaymentDao;
    }

    @Override
    public BookingPaymentDao update(BookingPaymentDao bookingPaymentDao) {
        BookingPaymentsRecord bookingPaymentsRecord = context.newRecord(BOOKING_PAYMENTS, bookingPaymentDao);
        bookingPaymentsRecord.update();


        bookingPaymentDao.setCreatedBy(bookingPaymentsRecord.getCreatedBy());
        bookingPaymentDao.setUpdatedBy(bookingPaymentsRecord.getUpdatedBy());
        bookingPaymentDao.setCreatedOn(bookingPaymentsRecord.getCreatedOn());
        bookingPaymentDao.setUpdatedOn(bookingPaymentsRecord.getUpdatedOn());
        bookingPaymentDao.setIsActive(bookingPaymentsRecord.getIsActive());
        bookingPaymentDao.setState(bookingPaymentsRecord.getState());
        bookingPaymentDao.setApiSource(bookingPaymentsRecord.getApiSource());
        bookingPaymentDao.setNotes(bookingPaymentsRecord.getNotes());
        return bookingPaymentDao;
    }

    @Override
    public void delete(Integer id) {
     context.update(BOOKING_PAYMENTS)
     .set(BOOKING_PAYMENTS.STATE,RecordState.DELETED.name())
     .where(BOOKING_PAYMENTS.ID.eq(id))
     .and(BOOKING_PAYMENTS.STATE.eq(RecordState.ACTIVE.name()))
     .and(BOOKING_PAYMENTS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public BookingPaymentDao findByUUID(String uuid) {
        return context.selectFrom(BOOKING_PAYMENTS).where(BOOKING_PAYMENTS.UUID.eq(uuid))
                .and(BOOKING_PAYMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingPaymentDao.class);
    }

    @Override
    public BookingPaymentDao findById(Integer id) {
        return context.selectFrom(BOOKING_PAYMENTS).where(BOOKING_PAYMENTS.ID.eq(id))
                .and(BOOKING_PAYMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingPaymentDao.class);
    }

    @Override
    public List<BookingPaymentDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKING_PAYMENTS).where(BOOKING_PAYMENTS.ID.in(ids)).fetchInto(BookingPaymentDao.class);
    }

    @Override
    public List<BookingPaymentDao> findAllActive() {
        return context.selectFrom(BOOKING_PAYMENTS).fetchInto(BookingPaymentDao.class);
    }
}
