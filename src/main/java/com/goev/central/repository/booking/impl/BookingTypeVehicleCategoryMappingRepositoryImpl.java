package com.goev.central.repository.booking.impl;


import com.goev.central.dao.booking.BookingTypeVehicleCategoryMappingDao;
import com.goev.central.repository.booking.BookingTypeVehicleCategoryMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BookingTypeVehicleCategoryMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BookingTypeVehicleCategoryMappings.BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class BookingTypeVehicleCategoryMappingRepositoryImpl implements BookingTypeVehicleCategoryMappingRepository {

    private final DSLContext context;

    @Override
    public BookingTypeVehicleCategoryMappingDao save(BookingTypeVehicleCategoryMappingDao bookingTypeVehicleCategoryMappingDao) {
        BookingTypeVehicleCategoryMappingsRecord bookingTypeVehicleCategoryMappingsRecord = context.newRecord(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS, bookingTypeVehicleCategoryMappingDao);
        bookingTypeVehicleCategoryMappingsRecord.store();
        bookingTypeVehicleCategoryMappingDao.setId(bookingTypeVehicleCategoryMappingsRecord.getId());
        bookingTypeVehicleCategoryMappingDao.setUuid(bookingTypeVehicleCategoryMappingsRecord.getUuid());
        bookingTypeVehicleCategoryMappingDao.setCreatedBy(bookingTypeVehicleCategoryMappingsRecord.getCreatedBy());
        bookingTypeVehicleCategoryMappingDao.setUpdatedBy(bookingTypeVehicleCategoryMappingsRecord.getUpdatedBy());
        bookingTypeVehicleCategoryMappingDao.setCreatedOn(bookingTypeVehicleCategoryMappingsRecord.getCreatedOn());
        bookingTypeVehicleCategoryMappingDao.setUpdatedOn(bookingTypeVehicleCategoryMappingsRecord.getUpdatedOn());
        bookingTypeVehicleCategoryMappingDao.setIsActive(bookingTypeVehicleCategoryMappingsRecord.getIsActive());
        bookingTypeVehicleCategoryMappingDao.setState(bookingTypeVehicleCategoryMappingsRecord.getState());
        bookingTypeVehicleCategoryMappingDao.setApiSource(bookingTypeVehicleCategoryMappingsRecord.getApiSource());
        bookingTypeVehicleCategoryMappingDao.setNotes(bookingTypeVehicleCategoryMappingsRecord.getNotes());
        return bookingTypeVehicleCategoryMappingDao;
    }

    @Override
    public BookingTypeVehicleCategoryMappingDao update(BookingTypeVehicleCategoryMappingDao bookingTypeVehicleCategoryMappingDao) {
        BookingTypeVehicleCategoryMappingsRecord bookingTypeVehicleCategoryMappingsRecord = context.newRecord(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS, bookingTypeVehicleCategoryMappingDao);
        bookingTypeVehicleCategoryMappingsRecord.update();


        bookingTypeVehicleCategoryMappingDao.setCreatedBy(bookingTypeVehicleCategoryMappingsRecord.getCreatedBy());
        bookingTypeVehicleCategoryMappingDao.setUpdatedBy(bookingTypeVehicleCategoryMappingsRecord.getUpdatedBy());
        bookingTypeVehicleCategoryMappingDao.setCreatedOn(bookingTypeVehicleCategoryMappingsRecord.getCreatedOn());
        bookingTypeVehicleCategoryMappingDao.setUpdatedOn(bookingTypeVehicleCategoryMappingsRecord.getUpdatedOn());
        bookingTypeVehicleCategoryMappingDao.setIsActive(bookingTypeVehicleCategoryMappingsRecord.getIsActive());
        bookingTypeVehicleCategoryMappingDao.setState(bookingTypeVehicleCategoryMappingsRecord.getState());
        bookingTypeVehicleCategoryMappingDao.setApiSource(bookingTypeVehicleCategoryMappingsRecord.getApiSource());
        bookingTypeVehicleCategoryMappingDao.setNotes(bookingTypeVehicleCategoryMappingsRecord.getNotes());
        return bookingTypeVehicleCategoryMappingDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS)
                .set(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS.STATE, RecordState.DELETED.name())
                .where(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS.ID.eq(id))
                .and(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public BookingTypeVehicleCategoryMappingDao findByUUID(String uuid) {
        return context.selectFrom(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS).where(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS.UUID.eq(uuid))
                .and(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingTypeVehicleCategoryMappingDao.class);
    }

    @Override
    public BookingTypeVehicleCategoryMappingDao findById(Integer id) {
        return context.selectFrom(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS).where(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS.ID.eq(id))
                .and(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BookingTypeVehicleCategoryMappingDao.class);
    }

    @Override
    public List<BookingTypeVehicleCategoryMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS).where(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS.ID.in(ids)).fetchInto(BookingTypeVehicleCategoryMappingDao.class);
    }

    @Override
    public List<BookingTypeVehicleCategoryMappingDao> findAllActive() {
        return context.selectFrom(BOOKING_TYPE_VEHICLE_CATEGORY_MAPPINGS).fetchInto(BookingTypeVehicleCategoryMappingDao.class);
    }
}
