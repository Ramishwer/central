package com.goev.central.repository.location.impl;

import com.goev.central.dao.location.LocationAttributeDao;
import com.goev.central.repository.location.LocationAttributeRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.LocationAttributesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.LocationAttributes.LOCATION_ATTRIBUTES;

@Slf4j
@Repository
@AllArgsConstructor
public class LocationAttributeRepositoryImpl implements LocationAttributeRepository {

    private final DSLContext context;

    @Override
    public LocationAttributeDao save(LocationAttributeDao locationAttribute) {
        LocationAttributesRecord locationAttributesRecord = context.newRecord(LOCATION_ATTRIBUTES, locationAttribute);
        locationAttributesRecord.store();
        locationAttribute.setId(locationAttributesRecord.getId());
        locationAttribute.setUuid(locationAttributesRecord.getUuid());
        locationAttribute.setCreatedBy(locationAttributesRecord.getCreatedBy());
        locationAttribute.setUpdatedBy(locationAttributesRecord.getUpdatedBy());
        locationAttribute.setCreatedOn(locationAttributesRecord.getCreatedOn());
        locationAttribute.setUpdatedOn(locationAttributesRecord.getUpdatedOn());
        locationAttribute.setIsActive(locationAttributesRecord.getIsActive());
        locationAttribute.setState(locationAttributesRecord.getState());
        locationAttribute.setApiSource(locationAttributesRecord.getApiSource());
        locationAttribute.setNotes(locationAttributesRecord.getNotes());
        return locationAttribute;
    }

    @Override
    public LocationAttributeDao update(LocationAttributeDao locationAttribute) {
        LocationAttributesRecord locationAttributesRecord = context.newRecord(LOCATION_ATTRIBUTES, locationAttribute);
        locationAttributesRecord.update();


        locationAttribute.setCreatedBy(locationAttributesRecord.getCreatedBy());
        locationAttribute.setUpdatedBy(locationAttributesRecord.getUpdatedBy());
        locationAttribute.setCreatedOn(locationAttributesRecord.getCreatedOn());
        locationAttribute.setUpdatedOn(locationAttributesRecord.getUpdatedOn());
        locationAttribute.setIsActive(locationAttributesRecord.getIsActive());
        locationAttribute.setState(locationAttributesRecord.getState());
        locationAttribute.setApiSource(locationAttributesRecord.getApiSource());
        locationAttribute.setNotes(locationAttributesRecord.getNotes());
        return locationAttribute;
    }

    @Override
    public void delete(Integer id) {
        context.update(LOCATION_ATTRIBUTES)
                .set(LOCATION_ATTRIBUTES.STATE, RecordState.DELETED.name())
                .where(LOCATION_ATTRIBUTES.ID.eq(id))
                .and(LOCATION_ATTRIBUTES.STATE.eq(RecordState.ACTIVE.name()))
                .and(LOCATION_ATTRIBUTES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public LocationAttributeDao findByUUID(String uuid) {
        return context.selectFrom(LOCATION_ATTRIBUTES).where(LOCATION_ATTRIBUTES.UUID.eq(uuid))
                .and(LOCATION_ATTRIBUTES.IS_ACTIVE.eq(true))
                .fetchAnyInto(LocationAttributeDao.class);
    }

    @Override
    public LocationAttributeDao findById(Integer id) {
        return context.selectFrom(LOCATION_ATTRIBUTES).where(LOCATION_ATTRIBUTES.ID.eq(id))
                .and(LOCATION_ATTRIBUTES.IS_ACTIVE.eq(true))
                .fetchAnyInto(LocationAttributeDao.class);
    }

    @Override
    public List<LocationAttributeDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(LOCATION_ATTRIBUTES).where(LOCATION_ATTRIBUTES.ID.in(ids)).fetchInto(LocationAttributeDao.class);
    }

    @Override
    public List<LocationAttributeDao> findAllActive() {
        return context.selectFrom(LOCATION_ATTRIBUTES).fetchInto(LocationAttributeDao.class);
    }
}
