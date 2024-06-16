package com.goev.central.repository.booking.impl;


import com.goev.central.dao.booking.BookingDetailDao;
import com.goev.central.repository.booking.BookingDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BookingDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BookingDetails.BOOKING_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class BookingDetailRepositoryImpl implements BookingDetailRepository {

    private final DSLContext context;

    @Override
    public BookingDetailDao save(BookingDetailDao bookingDetailDao) {
        BookingDetailsRecord bookingDetailsRecord = context.newRecord(BOOKING_DETAILS, bookingDetailDao);
        bookingDetailsRecord.store();
        bookingDetailDao.setId(bookingDetailsRecord.getId());
        bookingDetailDao.setUuid(bookingDetailsRecord.getUuid());
        bookingDetailDao.setCreatedBy(bookingDetailsRecord.getCreatedBy());
        bookingDetailDao.setUpdatedBy(bookingDetailsRecord.getUpdatedBy());
        bookingDetailDao.setCreatedOn(bookingDetailsRecord.getCreatedOn());
        bookingDetailDao.setUpdatedOn(bookingDetailsRecord.getUpdatedOn());
        bookingDetailDao.setIsActive(bookingDetailsRecord.getIsActive());
        bookingDetailDao.setState(bookingDetailsRecord.getState());
        bookingDetailDao.setApiSource(bookingDetailsRecord.getApiSource());
        bookingDetailDao.setNotes(bookingDetailsRecord.getNotes());
        return bookingDetailDao;
    }

    @Override
    public BookingDetailDao update(BookingDetailDao bookingDetailDao) {
        BookingDetailsRecord bookingDetailsRecord = context.newRecord(BOOKING_DETAILS, bookingDetailDao);
        bookingDetailsRecord.update();


        bookingDetailDao.setCreatedBy(bookingDetailsRecord.getCreatedBy());
        bookingDetailDao.setUpdatedBy(bookingDetailsRecord.getUpdatedBy());
        bookingDetailDao.setCreatedOn(bookingDetailsRecord.getCreatedOn());
        bookingDetailDao.setUpdatedOn(bookingDetailsRecord.getUpdatedOn());
        bookingDetailDao.setIsActive(bookingDetailsRecord.getIsActive());
        bookingDetailDao.setState(bookingDetailsRecord.getState());
        bookingDetailDao.setApiSource(bookingDetailsRecord.getApiSource());
        bookingDetailDao.setNotes(bookingDetailsRecord.getNotes());
        return bookingDetailDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKING_DETAILS)
                .set(BOOKING_DETAILS.STATE, RecordState.DELETED.name())
                .where(BOOKING_DETAILS.ID.eq(id))
                .and(BOOKING_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_DETAILS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public BookingDetailDao findByUUID(String uuid) {
        return context.selectFrom(BOOKING_DETAILS).where(BOOKING_DETAILS.UUID.eq(uuid))
                .and(BOOKING_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingDetailDao.class);
    }

    @Override
    public BookingDetailDao findById(Integer id) {
        return context.selectFrom(BOOKING_DETAILS).where(BOOKING_DETAILS.ID.eq(id))
                .and(BOOKING_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingDetailDao.class);
    }

    @Override
    public List<BookingDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKING_DETAILS).where(BOOKING_DETAILS.ID.in(ids)).fetchInto(BookingDetailDao.class);
    }

    @Override
    public List<BookingDetailDao> findAllActive() {
        return context.selectFrom(BOOKING_DETAILS).fetchInto(BookingDetailDao.class);
    }
}
