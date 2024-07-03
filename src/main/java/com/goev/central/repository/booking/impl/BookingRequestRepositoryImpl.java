package com.goev.central.repository.booking.impl;

import com.goev.central.dao.booking.BookingDao;
import com.goev.central.repository.booking.BookingRequestRepository;
import com.goev.central.service.booking.BookingRequestService;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BookingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.goev.record.central.tables.Bookings.BOOKINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class BookingRequestRepositoryImpl implements BookingRequestRepository {

    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public BookingDao save(BookingDao bookingDao) {
        BookingsRecord bookingsRecord = context.newRecord(BOOKINGS, bookingDao);
        bookingsRecord.store();
        bookingDao.setId(bookingsRecord.getId());
        bookingDao.setUuid(bookingsRecord.getUuid());
        bookingDao.setCreatedBy(bookingsRecord.getCreatedBy());
        bookingDao.setUpdatedBy(bookingsRecord.getUpdatedBy());
        bookingDao.setCreatedOn(bookingsRecord.getCreatedOn());
        bookingDao.setUpdatedOn(bookingsRecord.getUpdatedOn());
        bookingDao.setIsActive(bookingsRecord.getIsActive());
        bookingDao.setState(bookingsRecord.getState());
        bookingDao.setApiSource(bookingsRecord.getApiSource());
        bookingDao.setNotes(bookingsRecord.getNotes());


        if ("API".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("BookingSaveEvent", bookingDao);
        return bookingDao;
    }

    @Override
    public BookingDao update(BookingDao bookingDao) {
        BookingsRecord bookingsRecord = context.newRecord(BOOKINGS, bookingDao);
        bookingsRecord.update();


        bookingDao.setCreatedBy(bookingsRecord.getCreatedBy());
        bookingDao.setUpdatedBy(bookingsRecord.getUpdatedBy());
        bookingDao.setCreatedOn(bookingsRecord.getCreatedOn());
        bookingDao.setUpdatedOn(bookingsRecord.getUpdatedOn());
        bookingDao.setIsActive(bookingsRecord.getIsActive());
        bookingDao.setState(bookingsRecord.getState());
        bookingDao.setApiSource(bookingsRecord.getApiSource());
        bookingDao.setNotes(bookingsRecord.getNotes());
        if ("API".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("BookingUpdateEvent", bookingDao);
        return bookingDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKINGS)
                .set(BOOKINGS.STATE, RecordState.DELETED.name())
                .where(BOOKINGS.ID.eq(id))
                .and(BOOKINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public BookingDao findByUUID(String uuid) {
        return context.selectFrom(BOOKINGS).where(BOOKINGS.UUID.eq(uuid))
                .and(BOOKINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingDao.class);
    }

    @Override
    public BookingDao findById(Integer id) {
        return context.selectFrom(BOOKINGS).where(BOOKINGS.ID.eq(id))
                .and(BOOKINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingDao.class);
    }
}
