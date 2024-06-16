package com.goev.central.repository.booking.impl;


import com.goev.central.dao.booking.BookingFeedbackDao;
import com.goev.central.repository.booking.BookingFeedbackRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BookingFeedbacksRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BookingFeedbacks.BOOKING_FEEDBACKS;

@Slf4j
@Repository
@AllArgsConstructor
public class BookingFeedbackRepositoryImpl implements BookingFeedbackRepository {

    private final DSLContext context;

    @Override
    public BookingFeedbackDao save(BookingFeedbackDao bookingFeedbackDao) {
        BookingFeedbacksRecord bookingFeedbacksRecord = context.newRecord(BOOKING_FEEDBACKS, bookingFeedbackDao);
        bookingFeedbacksRecord.store();
        bookingFeedbackDao.setId(bookingFeedbacksRecord.getId());
        bookingFeedbackDao.setUuid(bookingFeedbacksRecord.getUuid());
        bookingFeedbackDao.setCreatedBy(bookingFeedbacksRecord.getCreatedBy());
        bookingFeedbackDao.setUpdatedBy(bookingFeedbacksRecord.getUpdatedBy());
        bookingFeedbackDao.setCreatedOn(bookingFeedbacksRecord.getCreatedOn());
        bookingFeedbackDao.setUpdatedOn(bookingFeedbacksRecord.getUpdatedOn());
        bookingFeedbackDao.setIsActive(bookingFeedbacksRecord.getIsActive());
        bookingFeedbackDao.setState(bookingFeedbacksRecord.getState());
        bookingFeedbackDao.setApiSource(bookingFeedbacksRecord.getApiSource());
        bookingFeedbackDao.setNotes(bookingFeedbacksRecord.getNotes());
        return bookingFeedbackDao;
    }

    @Override
    public BookingFeedbackDao update(BookingFeedbackDao bookingFeedbackDao) {
        BookingFeedbacksRecord bookingFeedbacksRecord = context.newRecord(BOOKING_FEEDBACKS, bookingFeedbackDao);
        bookingFeedbacksRecord.update();


        bookingFeedbackDao.setCreatedBy(bookingFeedbacksRecord.getCreatedBy());
        bookingFeedbackDao.setUpdatedBy(bookingFeedbacksRecord.getUpdatedBy());
        bookingFeedbackDao.setCreatedOn(bookingFeedbacksRecord.getCreatedOn());
        bookingFeedbackDao.setUpdatedOn(bookingFeedbacksRecord.getUpdatedOn());
        bookingFeedbackDao.setIsActive(bookingFeedbacksRecord.getIsActive());
        bookingFeedbackDao.setState(bookingFeedbacksRecord.getState());
        bookingFeedbackDao.setApiSource(bookingFeedbacksRecord.getApiSource());
        bookingFeedbackDao.setNotes(bookingFeedbacksRecord.getNotes());
        return bookingFeedbackDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKING_FEEDBACKS)
                .set(BOOKING_FEEDBACKS.STATE, RecordState.DELETED.name())
                .where(BOOKING_FEEDBACKS.ID.eq(id))
                .and(BOOKING_FEEDBACKS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_FEEDBACKS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public BookingFeedbackDao findByUUID(String uuid) {
        return context.selectFrom(BOOKING_FEEDBACKS).where(BOOKING_FEEDBACKS.UUID.eq(uuid))
                .and(BOOKING_FEEDBACKS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingFeedbackDao.class);
    }

    @Override
    public BookingFeedbackDao findById(Integer id) {
        return context.selectFrom(BOOKING_FEEDBACKS).where(BOOKING_FEEDBACKS.ID.eq(id))
                .and(BOOKING_FEEDBACKS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingFeedbackDao.class);
    }

    @Override
    public List<BookingFeedbackDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKING_FEEDBACKS).where(BOOKING_FEEDBACKS.ID.in(ids)).fetchInto(BookingFeedbackDao.class);
    }

    @Override
    public List<BookingFeedbackDao> findAllActive() {
        return context.selectFrom(BOOKING_FEEDBACKS).fetchInto(BookingFeedbackDao.class);
    }
}
