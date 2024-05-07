package com.goev.central.repository.booking.impl;


import com.goev.lib.enums.RecordState;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.record.central.tables.records.BookingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Bookings.BOOKINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {

    private final DSLContext context;

    @Override
    public BookingDao save(BookingDao bookingDao) {
        BookingsRecord bookingsRecord = context.newRecord(BOOKINGS, bookingDao);
        bookingsRecord.store();
        bookingDao.setId(bookingsRecord.getId());
        bookingDao.setUuid(bookingsRecord.getUuid());
        return bookingDao;
    }

    @Override
    public BookingDao update(BookingDao bookingDao) {
        BookingsRecord bookingsRecord = context.newRecord(BOOKINGS, bookingDao);
        bookingsRecord.update();
        return bookingDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKINGS).set(BOOKINGS.STATE, RecordState.DELETED.name()).where(BOOKINGS.ID.eq(id)).execute();
    }

    @Override
    public BookingDao findByUUID(String uuid) {
        return context.selectFrom(BOOKINGS).where(BOOKINGS.UUID.eq(uuid)).fetchAnyInto(BookingDao.class);
    }

    @Override
    public BookingDao findById(Integer id) {
        return context.selectFrom(BOOKINGS).where(BOOKINGS.ID.eq(id)).fetchAnyInto(BookingDao.class);
    }

    @Override
    public List<BookingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKINGS).where(BOOKINGS.ID.in(ids)).fetchInto(BookingDao.class);
    }

    @Override
    public List<BookingDao> findAll() {
        return context.selectFrom(BOOKINGS).fetchInto(BookingDao.class);
    }
}
