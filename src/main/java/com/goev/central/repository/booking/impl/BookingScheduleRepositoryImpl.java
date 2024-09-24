package com.goev.central.repository.booking.impl;


import com.goev.central.dao.booking.BookingScheduleDao;
import com.goev.central.enums.booking.BookingScheduleStatus;
import com.goev.central.repository.booking.BookingScheduleRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BookingSchedulesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BookingSchedules.BOOKING_SCHEDULES;

@Slf4j
@Repository
@AllArgsConstructor
public class BookingScheduleRepositoryImpl implements BookingScheduleRepository {

    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public BookingScheduleDao save(BookingScheduleDao bookingScheduleDao) {
        BookingSchedulesRecord bookingSchedulesRecord = context.newRecord(BOOKING_SCHEDULES, bookingScheduleDao);
        bookingSchedulesRecord.store();
        bookingScheduleDao.setId(bookingSchedulesRecord.getId());
        bookingScheduleDao.setUuid(bookingSchedulesRecord.getUuid());
        bookingScheduleDao.setCreatedBy(bookingSchedulesRecord.getCreatedBy());
        bookingScheduleDao.setUpdatedBy(bookingSchedulesRecord.getUpdatedBy());
        bookingScheduleDao.setCreatedOn(bookingSchedulesRecord.getCreatedOn());
        bookingScheduleDao.setUpdatedOn(bookingSchedulesRecord.getUpdatedOn());
        bookingScheduleDao.setIsActive(bookingSchedulesRecord.getIsActive());
        bookingScheduleDao.setState(bookingSchedulesRecord.getState());
        bookingScheduleDao.setApiSource(bookingSchedulesRecord.getApiSource());
        bookingScheduleDao.setNotes(bookingSchedulesRecord.getNotes());


        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("BookingScheduleSaveEvent", bookingScheduleDao);
        return bookingScheduleDao;
    }

    @Override
    public BookingScheduleDao update(BookingScheduleDao bookingScheduleDao) {
        BookingSchedulesRecord bookingSchedulesRecord = context.newRecord(BOOKING_SCHEDULES, bookingScheduleDao);
        bookingSchedulesRecord.update();


        bookingScheduleDao.setCreatedBy(bookingSchedulesRecord.getCreatedBy());
        bookingScheduleDao.setUpdatedBy(bookingSchedulesRecord.getUpdatedBy());
        bookingScheduleDao.setCreatedOn(bookingSchedulesRecord.getCreatedOn());
        bookingScheduleDao.setUpdatedOn(bookingSchedulesRecord.getUpdatedOn());
        bookingScheduleDao.setIsActive(bookingSchedulesRecord.getIsActive());
        bookingScheduleDao.setState(bookingSchedulesRecord.getState());
        bookingScheduleDao.setApiSource(bookingSchedulesRecord.getApiSource());
        bookingScheduleDao.setNotes(bookingSchedulesRecord.getNotes());
        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("BookingScheduleUpdateEvent", bookingScheduleDao);
        return bookingScheduleDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKING_SCHEDULES)
                .set(BOOKING_SCHEDULES.STATE, RecordState.DELETED.name())
                .where(BOOKING_SCHEDULES.ID.eq(id))
                .and(BOOKING_SCHEDULES.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_SCHEDULES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public BookingScheduleDao findByUUID(String uuid) {
        return context.selectFrom(BOOKING_SCHEDULES).where(BOOKING_SCHEDULES.UUID.eq(uuid))
                .and(BOOKING_SCHEDULES.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingScheduleDao.class);
    }

    @Override
    public BookingScheduleDao findById(Integer id) {
        return context.selectFrom(BOOKING_SCHEDULES).where(BOOKING_SCHEDULES.ID.eq(id))
                .and(BOOKING_SCHEDULES.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingScheduleDao.class);
    }

    @Override
    public List<BookingScheduleDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKING_SCHEDULES).where(BOOKING_SCHEDULES.ID.in(ids)).fetchInto(BookingScheduleDao.class);
    }

    @Override
    public List<BookingScheduleDao> findAllActive(String status, String subStatus) {
        if(subStatus == null)
            return context.selectFrom(BOOKING_SCHEDULES)
                    .where(BOOKING_SCHEDULES.STATUS.eq(status))
                    .and(BOOKING_SCHEDULES.STATE.eq(RecordState.ACTIVE.name()))
                    .and(BOOKING_SCHEDULES.IS_ACTIVE.eq(true))
                    .fetchInto(BookingScheduleDao.class);

        return context.selectFrom(BOOKING_SCHEDULES)
                .where(BOOKING_SCHEDULES.STATUS.eq(status))
                .and(BOOKING_SCHEDULES.SUB_STATUS.eq(subStatus))
                .and(BOOKING_SCHEDULES.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_SCHEDULES.IS_ACTIVE.eq(true))
                .fetchInto(BookingScheduleDao.class);
    }

    @Override
    public List<BookingScheduleDao> findAllActiveWithTimeBetween(DateTime start) {
        return context.selectFrom(BOOKING_SCHEDULES)
                .where(BOOKING_SCHEDULES.STATUS.in(BookingScheduleStatus.CONFIRMED.name(),BookingScheduleStatus.IN_PROGRESS.name()))
                .and(BOOKING_SCHEDULES.APPLICABLE_FROM_TIME.le(start))
                .and(BOOKING_SCHEDULES.APPLICABLE_TO_TIME.ge(start).or(BOOKING_SCHEDULES.APPLICABLE_TO_TIME.isNull()))
                .and(BOOKING_SCHEDULES.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_SCHEDULES.IS_ACTIVE.eq(true))
                .fetchInto(BookingScheduleDao.class);
    }
}
