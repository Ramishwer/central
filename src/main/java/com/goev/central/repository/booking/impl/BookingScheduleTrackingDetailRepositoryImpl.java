package com.goev.central.repository.booking.impl;


import com.goev.central.dao.booking.BookingScheduleTrackingDetailDao;
import com.goev.central.repository.booking.BookingScheduleTrackingDetailRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BookingScheduleTrackingDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BookingScheduleTrackingDetails.BOOKING_SCHEDULE_TRACKING_DETAILS;


@Slf4j
@Repository
@AllArgsConstructor
public class BookingScheduleTrackingDetailRepositoryImpl implements BookingScheduleTrackingDetailRepository {

    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public BookingScheduleTrackingDetailDao save(BookingScheduleTrackingDetailDao bookingScheduleTrackingDetailDao) {
        BookingScheduleTrackingDetailsRecord bookingScheduleTrackingDetailRecord = context.newRecord(BOOKING_SCHEDULE_TRACKING_DETAILS, bookingScheduleTrackingDetailDao);
        bookingScheduleTrackingDetailRecord.store();
        bookingScheduleTrackingDetailDao.setId(bookingScheduleTrackingDetailRecord.getId());
        bookingScheduleTrackingDetailDao.setUuid(bookingScheduleTrackingDetailRecord.getUuid());
        bookingScheduleTrackingDetailDao.setCreatedBy(bookingScheduleTrackingDetailRecord.getCreatedBy());
        bookingScheduleTrackingDetailDao.setUpdatedBy(bookingScheduleTrackingDetailRecord.getUpdatedBy());
        bookingScheduleTrackingDetailDao.setCreatedOn(bookingScheduleTrackingDetailRecord.getCreatedOn());
        bookingScheduleTrackingDetailDao.setUpdatedOn(bookingScheduleTrackingDetailRecord.getUpdatedOn());
        bookingScheduleTrackingDetailDao.setIsActive(bookingScheduleTrackingDetailRecord.getIsActive());
        bookingScheduleTrackingDetailDao.setState(bookingScheduleTrackingDetailRecord.getState());
        bookingScheduleTrackingDetailDao.setApiSource(bookingScheduleTrackingDetailRecord.getApiSource());
        bookingScheduleTrackingDetailDao.setNotes(bookingScheduleTrackingDetailRecord.getNotes());

        return bookingScheduleTrackingDetailDao;
    }

    @Override
    public BookingScheduleTrackingDetailDao update(BookingScheduleTrackingDetailDao bookingScheduleTrackingDetailDao) {
        BookingScheduleTrackingDetailsRecord bookingScheduleTrackingDetailRecord = context.newRecord(BOOKING_SCHEDULE_TRACKING_DETAILS, bookingScheduleTrackingDetailDao);
        bookingScheduleTrackingDetailRecord.update();


        bookingScheduleTrackingDetailDao.setCreatedBy(bookingScheduleTrackingDetailRecord.getCreatedBy());
        bookingScheduleTrackingDetailDao.setUpdatedBy(bookingScheduleTrackingDetailRecord.getUpdatedBy());
        bookingScheduleTrackingDetailDao.setCreatedOn(bookingScheduleTrackingDetailRecord.getCreatedOn());
        bookingScheduleTrackingDetailDao.setUpdatedOn(bookingScheduleTrackingDetailRecord.getUpdatedOn());
        bookingScheduleTrackingDetailDao.setIsActive(bookingScheduleTrackingDetailRecord.getIsActive());
        bookingScheduleTrackingDetailDao.setState(bookingScheduleTrackingDetailRecord.getState());
        bookingScheduleTrackingDetailDao.setApiSource(bookingScheduleTrackingDetailRecord.getApiSource());
        bookingScheduleTrackingDetailDao.setNotes(bookingScheduleTrackingDetailRecord.getNotes());

        return bookingScheduleTrackingDetailDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKING_SCHEDULE_TRACKING_DETAILS)
                .set(BOOKING_SCHEDULE_TRACKING_DETAILS.STATE, RecordState.DELETED.name())
                .where(BOOKING_SCHEDULE_TRACKING_DETAILS.ID.eq(id))
                .and(BOOKING_SCHEDULE_TRACKING_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_SCHEDULE_TRACKING_DETAILS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public BookingScheduleTrackingDetailDao findByUUID(String uuid) {
        return context.selectFrom(BOOKING_SCHEDULE_TRACKING_DETAILS).where(BOOKING_SCHEDULE_TRACKING_DETAILS.UUID.eq(uuid))
                .and(BOOKING_SCHEDULE_TRACKING_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingScheduleTrackingDetailDao.class);
    }

    @Override
    public BookingScheduleTrackingDetailDao findById(Integer id) {
        return context.selectFrom(BOOKING_SCHEDULE_TRACKING_DETAILS).where(BOOKING_SCHEDULE_TRACKING_DETAILS.ID.eq(id))
                .and(BOOKING_SCHEDULE_TRACKING_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingScheduleTrackingDetailDao.class);
    }

    @Override
    public List<BookingScheduleTrackingDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKING_SCHEDULE_TRACKING_DETAILS).where(BOOKING_SCHEDULE_TRACKING_DETAILS.ID.in(ids)).fetchInto(BookingScheduleTrackingDetailDao.class);
    }

    @Override
    public List<BookingScheduleTrackingDetailDao> findAllActive(String status, String subStatus) {
        if (subStatus == null)
            return context.selectFrom(BOOKING_SCHEDULE_TRACKING_DETAILS)
                    .where(BOOKING_SCHEDULE_TRACKING_DETAILS.STATUS.eq(status))
                    .and(BOOKING_SCHEDULE_TRACKING_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                    .and(BOOKING_SCHEDULE_TRACKING_DETAILS.IS_ACTIVE.eq(true))
                    .fetchInto(BookingScheduleTrackingDetailDao.class);

        return context.selectFrom(BOOKING_SCHEDULE_TRACKING_DETAILS)
                .where(BOOKING_SCHEDULE_TRACKING_DETAILS.STATUS.eq(status))
                .and(BOOKING_SCHEDULE_TRACKING_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_SCHEDULE_TRACKING_DETAILS.IS_ACTIVE.eq(true))
                .fetchInto(BookingScheduleTrackingDetailDao.class);
    }

    @Override
    public BookingScheduleTrackingDetailDao findByBookingScheduleIdDayDate(Integer scheduleId, String day, DateTime date) {
        return context.selectFrom(BOOKING_SCHEDULE_TRACKING_DETAILS)
                .where(BOOKING_SCHEDULE_TRACKING_DETAILS.BOOKING_SCHEDULE_ID.eq(scheduleId))
                .and(BOOKING_SCHEDULE_TRACKING_DETAILS.DAY.eq(day))
                .and(BOOKING_SCHEDULE_TRACKING_DETAILS.BOOKING_DATE.eq(date))
                .and(BOOKING_SCHEDULE_TRACKING_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_SCHEDULE_TRACKING_DETAILS.IS_ACTIVE.eq(true))
                .fetchOneInto(BookingScheduleTrackingDetailDao.class);
    }

}
