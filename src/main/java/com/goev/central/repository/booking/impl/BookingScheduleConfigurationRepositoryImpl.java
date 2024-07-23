package com.goev.central.repository.booking.impl;


import com.goev.central.dao.booking.BookingScheduleConfigurationDao;
import com.goev.central.repository.booking.BookingScheduleConfigurationRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BookingScheduleConfigurationsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BookingScheduleConfigurations.BOOKING_SCHEDULE_CONFIGURATIONS;


@Slf4j
@Repository
@AllArgsConstructor
public class BookingScheduleConfigurationRepositoryImpl implements BookingScheduleConfigurationRepository {

    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public BookingScheduleConfigurationDao save(BookingScheduleConfigurationDao bookingScheduleConfigurationDao) {
        BookingScheduleConfigurationsRecord bookingScheduleConfigurationRecord = context.newRecord(BOOKING_SCHEDULE_CONFIGURATIONS, bookingScheduleConfigurationDao);
        bookingScheduleConfigurationRecord.store();
        bookingScheduleConfigurationDao.setId(bookingScheduleConfigurationRecord.getId());
        bookingScheduleConfigurationDao.setUuid(bookingScheduleConfigurationRecord.getUuid());
        bookingScheduleConfigurationDao.setCreatedBy(bookingScheduleConfigurationRecord.getCreatedBy());
        bookingScheduleConfigurationDao.setUpdatedBy(bookingScheduleConfigurationRecord.getUpdatedBy());
        bookingScheduleConfigurationDao.setCreatedOn(bookingScheduleConfigurationRecord.getCreatedOn());
        bookingScheduleConfigurationDao.setUpdatedOn(bookingScheduleConfigurationRecord.getUpdatedOn());
        bookingScheduleConfigurationDao.setIsActive(bookingScheduleConfigurationRecord.getIsActive());
        bookingScheduleConfigurationDao.setState(bookingScheduleConfigurationRecord.getState());
        bookingScheduleConfigurationDao.setApiSource(bookingScheduleConfigurationRecord.getApiSource());
        bookingScheduleConfigurationDao.setNotes(bookingScheduleConfigurationRecord.getNotes());

        return bookingScheduleConfigurationDao;
    }

    @Override
    public BookingScheduleConfigurationDao update(BookingScheduleConfigurationDao bookingScheduleConfigurationDao) {
        BookingScheduleConfigurationsRecord bookingScheduleConfigurationRecord = context.newRecord(BOOKING_SCHEDULE_CONFIGURATIONS, bookingScheduleConfigurationDao);
        bookingScheduleConfigurationRecord.update();


        bookingScheduleConfigurationDao.setCreatedBy(bookingScheduleConfigurationRecord.getCreatedBy());
        bookingScheduleConfigurationDao.setUpdatedBy(bookingScheduleConfigurationRecord.getUpdatedBy());
        bookingScheduleConfigurationDao.setCreatedOn(bookingScheduleConfigurationRecord.getCreatedOn());
        bookingScheduleConfigurationDao.setUpdatedOn(bookingScheduleConfigurationRecord.getUpdatedOn());
        bookingScheduleConfigurationDao.setIsActive(bookingScheduleConfigurationRecord.getIsActive());
        bookingScheduleConfigurationDao.setState(bookingScheduleConfigurationRecord.getState());
        bookingScheduleConfigurationDao.setApiSource(bookingScheduleConfigurationRecord.getApiSource());
        bookingScheduleConfigurationDao.setNotes(bookingScheduleConfigurationRecord.getNotes());

        return bookingScheduleConfigurationDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKING_SCHEDULE_CONFIGURATIONS)
                .set(BOOKING_SCHEDULE_CONFIGURATIONS.STATE, RecordState.DELETED.name())
                .where(BOOKING_SCHEDULE_CONFIGURATIONS.ID.eq(id))
                .and(BOOKING_SCHEDULE_CONFIGURATIONS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_SCHEDULE_CONFIGURATIONS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public BookingScheduleConfigurationDao findByUUID(String uuid) {
        return context.selectFrom(BOOKING_SCHEDULE_CONFIGURATIONS).where(BOOKING_SCHEDULE_CONFIGURATIONS.UUID.eq(uuid))
                .and(BOOKING_SCHEDULE_CONFIGURATIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingScheduleConfigurationDao.class);
    }

    @Override
    public BookingScheduleConfigurationDao findById(Integer id) {
        return context.selectFrom(BOOKING_SCHEDULE_CONFIGURATIONS).where(BOOKING_SCHEDULE_CONFIGURATIONS.ID.eq(id))
                .and(BOOKING_SCHEDULE_CONFIGURATIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingScheduleConfigurationDao.class);
    }

    @Override
    public List<BookingScheduleConfigurationDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKING_SCHEDULE_CONFIGURATIONS).where(BOOKING_SCHEDULE_CONFIGURATIONS.ID.in(ids)).fetchInto(BookingScheduleConfigurationDao.class);
    }

    @Override
    public List<BookingScheduleConfigurationDao> findAllActive(String status, String subStatus) {
        if (subStatus == null)
            return context.selectFrom(BOOKING_SCHEDULE_CONFIGURATIONS)
                    .where(BOOKING_SCHEDULE_CONFIGURATIONS.STATUS.eq(status))
                    .and(BOOKING_SCHEDULE_CONFIGURATIONS.STATE.eq(RecordState.ACTIVE.name()))
                    .and(BOOKING_SCHEDULE_CONFIGURATIONS.IS_ACTIVE.eq(true))
                    .fetchInto(BookingScheduleConfigurationDao.class);

        return context.selectFrom(BOOKING_SCHEDULE_CONFIGURATIONS)
                .where(BOOKING_SCHEDULE_CONFIGURATIONS.STATUS.eq(status))
                .and(BOOKING_SCHEDULE_CONFIGURATIONS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_SCHEDULE_CONFIGURATIONS.IS_ACTIVE.eq(true))
                .fetchInto(BookingScheduleConfigurationDao.class);
    }

    @Override
    public BookingScheduleConfigurationDao findByBookingScheduleIdAndDay(Integer bookingScheduleId, String day) {
        return context.selectFrom(BOOKING_SCHEDULE_CONFIGURATIONS)
                .where(BOOKING_SCHEDULE_CONFIGURATIONS.BOOKING_SCHEDULE_ID.eq(bookingScheduleId))
                .and(BOOKING_SCHEDULE_CONFIGURATIONS.DAY.eq(day))
                .and(BOOKING_SCHEDULE_CONFIGURATIONS.IS_ACTIVE.eq(true))
                .and(BOOKING_SCHEDULE_CONFIGURATIONS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchAnyInto(BookingScheduleConfigurationDao.class);
    }

}
