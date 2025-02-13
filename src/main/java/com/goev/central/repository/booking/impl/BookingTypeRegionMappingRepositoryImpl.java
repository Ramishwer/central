package com.goev.central.repository.booking.impl;


import com.goev.central.dao.booking.BookingTypeRegionMappingDao;
import com.goev.central.repository.booking.BookingTypeRegionMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BookingTypeRegionMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BookingTypeRegionMappings.BOOKING_TYPE_REGION_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class BookingTypeRegionMappingRepositoryImpl implements BookingTypeRegionMappingRepository {

    private final DSLContext context;

    @Override
    public BookingTypeRegionMappingDao save(BookingTypeRegionMappingDao bookingTypeRegionMappingDao) {
        BookingTypeRegionMappingsRecord bookingTypeRegionMappingsRecord = context.newRecord(BOOKING_TYPE_REGION_MAPPINGS, bookingTypeRegionMappingDao);
        bookingTypeRegionMappingsRecord.store();
        bookingTypeRegionMappingDao.setId(bookingTypeRegionMappingsRecord.getId());
        bookingTypeRegionMappingDao.setUuid(bookingTypeRegionMappingsRecord.getUuid());
        bookingTypeRegionMappingDao.setCreatedBy(bookingTypeRegionMappingsRecord.getCreatedBy());
        bookingTypeRegionMappingDao.setUpdatedBy(bookingTypeRegionMappingsRecord.getUpdatedBy());
        bookingTypeRegionMappingDao.setCreatedOn(bookingTypeRegionMappingsRecord.getCreatedOn());
        bookingTypeRegionMappingDao.setUpdatedOn(bookingTypeRegionMappingsRecord.getUpdatedOn());
        bookingTypeRegionMappingDao.setIsActive(bookingTypeRegionMappingsRecord.getIsActive());
        bookingTypeRegionMappingDao.setState(bookingTypeRegionMappingsRecord.getState());
        bookingTypeRegionMappingDao.setApiSource(bookingTypeRegionMappingsRecord.getApiSource());
        bookingTypeRegionMappingDao.setNotes(bookingTypeRegionMappingsRecord.getNotes());
        return bookingTypeRegionMappingDao;
    }

    @Override
    public BookingTypeRegionMappingDao update(BookingTypeRegionMappingDao bookingTypeRegionMappingDao) {
        BookingTypeRegionMappingsRecord bookingTypeRegionMappingsRecord = context.newRecord(BOOKING_TYPE_REGION_MAPPINGS, bookingTypeRegionMappingDao);
        bookingTypeRegionMappingsRecord.update();


        bookingTypeRegionMappingDao.setCreatedBy(bookingTypeRegionMappingsRecord.getCreatedBy());
        bookingTypeRegionMappingDao.setUpdatedBy(bookingTypeRegionMappingsRecord.getUpdatedBy());
        bookingTypeRegionMappingDao.setCreatedOn(bookingTypeRegionMappingsRecord.getCreatedOn());
        bookingTypeRegionMappingDao.setUpdatedOn(bookingTypeRegionMappingsRecord.getUpdatedOn());
        bookingTypeRegionMappingDao.setIsActive(bookingTypeRegionMappingsRecord.getIsActive());
        bookingTypeRegionMappingDao.setState(bookingTypeRegionMappingsRecord.getState());
        bookingTypeRegionMappingDao.setApiSource(bookingTypeRegionMappingsRecord.getApiSource());
        bookingTypeRegionMappingDao.setNotes(bookingTypeRegionMappingsRecord.getNotes());
        return bookingTypeRegionMappingDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKING_TYPE_REGION_MAPPINGS)
                .set(BOOKING_TYPE_REGION_MAPPINGS.STATE, RecordState.DELETED.name())
                .where(BOOKING_TYPE_REGION_MAPPINGS.ID.eq(id))
                .and(BOOKING_TYPE_REGION_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_TYPE_REGION_MAPPINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public BookingTypeRegionMappingDao findByUUID(String uuid) {
        return context.selectFrom(BOOKING_TYPE_REGION_MAPPINGS).where(BOOKING_TYPE_REGION_MAPPINGS.UUID.eq(uuid))
                .and(BOOKING_TYPE_REGION_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingTypeRegionMappingDao.class);
    }

    @Override
    public BookingTypeRegionMappingDao findById(Integer id) {
        return context.selectFrom(BOOKING_TYPE_REGION_MAPPINGS).where(BOOKING_TYPE_REGION_MAPPINGS.ID.eq(id))
                .and(BOOKING_TYPE_REGION_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingTypeRegionMappingDao.class);
    }

    @Override
    public List<BookingTypeRegionMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKING_TYPE_REGION_MAPPINGS).where(BOOKING_TYPE_REGION_MAPPINGS.ID.in(ids)).fetchInto(BookingTypeRegionMappingDao.class);
    }

    @Override
    public List<BookingTypeRegionMappingDao> findAllActive() {
        return context.selectFrom(BOOKING_TYPE_REGION_MAPPINGS).fetchInto(BookingTypeRegionMappingDao.class);
    }
}
