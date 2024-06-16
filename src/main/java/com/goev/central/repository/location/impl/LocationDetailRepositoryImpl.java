package com.goev.central.repository.location.impl;

import com.goev.central.dao.location.LocationDetailDao;
import com.goev.central.repository.location.LocationDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.LocationDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.LocationDetails.LOCATION_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class LocationDetailRepositoryImpl implements LocationDetailRepository {

    private final DSLContext context;

    @Override
    public LocationDetailDao save(LocationDetailDao locationDetail) {
        LocationDetailsRecord locationDetailsRecord = context.newRecord(LOCATION_DETAILS, locationDetail);
        locationDetailsRecord.store();
        locationDetail.setId(locationDetailsRecord.getId());
        locationDetail.setUuid(locationDetailsRecord.getUuid());
        locationDetail.setCreatedBy(locationDetailsRecord.getCreatedBy());
        locationDetail.setUpdatedBy(locationDetailsRecord.getUpdatedBy());
        locationDetail.setCreatedOn(locationDetailsRecord.getCreatedOn());
        locationDetail.setUpdatedOn(locationDetailsRecord.getUpdatedOn());
        locationDetail.setIsActive(locationDetailsRecord.getIsActive());
        locationDetail.setState(locationDetailsRecord.getState());
        locationDetail.setApiSource(locationDetailsRecord.getApiSource());
        locationDetail.setNotes(locationDetailsRecord.getNotes());
        return locationDetail;
    }

    @Override
    public LocationDetailDao update(LocationDetailDao locationDetail) {
        LocationDetailsRecord locationDetailsRecord = context.newRecord(LOCATION_DETAILS, locationDetail);
        locationDetailsRecord.update();


        locationDetail.setCreatedBy(locationDetailsRecord.getCreatedBy());
        locationDetail.setUpdatedBy(locationDetailsRecord.getUpdatedBy());
        locationDetail.setCreatedOn(locationDetailsRecord.getCreatedOn());
        locationDetail.setUpdatedOn(locationDetailsRecord.getUpdatedOn());
        locationDetail.setIsActive(locationDetailsRecord.getIsActive());
        locationDetail.setState(locationDetailsRecord.getState());
        locationDetail.setApiSource(locationDetailsRecord.getApiSource());
        locationDetail.setNotes(locationDetailsRecord.getNotes());
        return locationDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(LOCATION_DETAILS)
                .set(LOCATION_DETAILS.STATE, RecordState.DELETED.name())
                .where(LOCATION_DETAILS.ID.eq(id))
                .and(LOCATION_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(LOCATION_DETAILS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public LocationDetailDao findByUUID(String uuid) {
        return context.selectFrom(LOCATION_DETAILS).where(LOCATION_DETAILS.UUID.eq(uuid))
                .and(LOCATION_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(LocationDetailDao.class);
    }

    @Override
    public LocationDetailDao findById(Integer id) {
        return context.selectFrom(LOCATION_DETAILS).where(LOCATION_DETAILS.ID.eq(id))
                .and(LOCATION_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(LocationDetailDao.class);
    }

    @Override
    public List<LocationDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(LOCATION_DETAILS).where(LOCATION_DETAILS.ID.in(ids)).fetchInto(LocationDetailDao.class);
    }

    @Override
    public List<LocationDetailDao> findAllActive() {
        return context.selectFrom(LOCATION_DETAILS).fetchInto(LocationDetailDao.class);
    }
}
