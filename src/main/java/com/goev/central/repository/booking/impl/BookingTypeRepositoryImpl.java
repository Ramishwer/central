package com.goev.central.repository.booking.impl;


import com.goev.lib.enums.RecordState;
import com.goev.central.dao.booking.BookingTypeDao;
import com.goev.central.repository.booking.BookingTypeRepository;
import com.goev.record.central.tables.records.BookingTypesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BookingTypes.BOOKING_TYPES;

@Slf4j
@Repository
@AllArgsConstructor
public class BookingTypeRepositoryImpl implements BookingTypeRepository {

    private final DSLContext context;

    @Override
    public BookingTypeDao save(BookingTypeDao bookingTypeDao) {
        BookingTypesRecord bookingTypesRecord = context.newRecord(BOOKING_TYPES, bookingTypeDao);
        bookingTypesRecord.store();
        bookingTypeDao.setId(bookingTypesRecord.getId());
        bookingTypeDao.setUuid(bookingTypesRecord.getUuid());
        return bookingTypeDao;
    }

    @Override
    public BookingTypeDao update(BookingTypeDao bookingTypeDao) {
        BookingTypesRecord bookingTypesRecord = context.newRecord(BOOKING_TYPES, bookingTypeDao);
        bookingTypesRecord.update();
        return bookingTypeDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKING_TYPES).set(BOOKING_TYPES.STATE, RecordState.DELETED.name()).where(BOOKING_TYPES.ID.eq(id)).execute();
    }

    @Override
    public BookingTypeDao findByUUID(String uuid) {
        return context.selectFrom(BOOKING_TYPES).where(BOOKING_TYPES.UUID.eq(uuid)).fetchAnyInto(BookingTypeDao.class);
    }

    @Override
    public BookingTypeDao findById(Integer id) {
        return context.selectFrom(BOOKING_TYPES).where(BOOKING_TYPES.ID.eq(id)).fetchAnyInto(BookingTypeDao.class);
    }

    @Override
    public List<BookingTypeDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKING_TYPES).where(BOOKING_TYPES.ID.in(ids)).fetchInto(BookingTypeDao.class);
    }

    @Override
    public List<BookingTypeDao> findAll() {
        return context.selectFrom(BOOKING_TYPES).fetchInto(BookingTypeDao.class);
    }
}
