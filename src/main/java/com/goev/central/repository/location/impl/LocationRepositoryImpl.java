package com.goev.central.repository.location.impl;

import com.goev.central.dao.location.LocationDao;
import com.goev.central.repository.location.LocationRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.LocationsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Locations.LOCATIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private final DSLContext context;

    @Override
    public LocationDao save(LocationDao location) {
        LocationsRecord locationsRecord = context.newRecord(LOCATIONS, location);
        locationsRecord.store();
        location.setId(locationsRecord.getId());
        location.setUuid(locationsRecord.getUuid());
        location.setCreatedBy(locationsRecord.getCreatedBy());
        location.setUpdatedBy(locationsRecord.getUpdatedBy());
        location.setCreatedOn(locationsRecord.getCreatedOn());
        location.setUpdatedOn(locationsRecord.getUpdatedOn());
        location.setIsActive(locationsRecord.getIsActive());
        location.setState(locationsRecord.getState());
        location.setApiSource(locationsRecord.getApiSource());
        location.setNotes(locationsRecord.getNotes());
        return location;
    }

    @Override
    public LocationDao update(LocationDao location) {
        LocationsRecord locationsRecord = context.newRecord(LOCATIONS, location);
        locationsRecord.update();


        location.setCreatedBy(locationsRecord.getCreatedBy());
        location.setUpdatedBy(locationsRecord.getUpdatedBy());
        location.setCreatedOn(locationsRecord.getCreatedOn());
        location.setUpdatedOn(locationsRecord.getUpdatedOn());
        location.setIsActive(locationsRecord.getIsActive());
        location.setState(locationsRecord.getState());
        location.setApiSource(locationsRecord.getApiSource());
        location.setNotes(locationsRecord.getNotes());
        return location;
    }

    @Override
    public void delete(Integer id) {
        context.update(LOCATIONS)
                .set(LOCATIONS.STATE, RecordState.DELETED.name())
                .where(LOCATIONS.ID.eq(id))
                .and(LOCATIONS.STATE.eq(RecordState.ACTIVE.name()))
                .and(LOCATIONS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public LocationDao findByUUID(String uuid) {
        return context.selectFrom(LOCATIONS).where(LOCATIONS.UUID.eq(uuid))
                .and(LOCATIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(LocationDao.class);
    }

    @Override
    public LocationDao findById(Integer id) {
        return context.selectFrom(LOCATIONS).where(LOCATIONS.ID.eq(id))
                .and(LOCATIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(LocationDao.class);
    }

    @Override
    public List<LocationDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(LOCATIONS).where(LOCATIONS.ID.in(ids)).fetchInto(LocationDao.class);
    }

    @Override
    public List<LocationDao> findAllActive() {
        return context.selectFrom(LOCATIONS).fetchInto(LocationDao.class);
    }
}
