package com.goev.central.repository.region.impl;

import com.goev.central.dao.region.RegionTypeDao;
import com.goev.central.repository.region.RegionTypeRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.RegionTypesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.RegionTypes.REGION_TYPES;

@Slf4j
@Repository
@AllArgsConstructor
public class RegionTypeRepositoryImpl implements RegionTypeRepository {
    private final DSLContext context;

    @Override
    public RegionTypeDao save(RegionTypeDao log) {
        RegionTypesRecord regionTypesRecord = context.newRecord(REGION_TYPES, log);
        regionTypesRecord.store();
        log.setId(regionTypesRecord.getId());
        log.setUuid(regionTypesRecord.getUuid());
        log.setCreatedBy(regionTypesRecord.getCreatedBy());
        log.setUpdatedBy(regionTypesRecord.getUpdatedBy());
        log.setCreatedOn(regionTypesRecord.getCreatedOn());
        log.setUpdatedOn(regionTypesRecord.getUpdatedOn());
        log.setIsActive(regionTypesRecord.getIsActive());
        log.setState(regionTypesRecord.getState());
        log.setApiSource(regionTypesRecord.getApiSource());
        log.setNotes(regionTypesRecord.getNotes());
        return log;
    }

    @Override
    public RegionTypeDao update(RegionTypeDao log) {
        RegionTypesRecord regionTypesRecord = context.newRecord(REGION_TYPES, log);
        regionTypesRecord.update();


        log.setCreatedBy(regionTypesRecord.getCreatedBy());
        log.setUpdatedBy(regionTypesRecord.getUpdatedBy());
        log.setCreatedOn(regionTypesRecord.getCreatedOn());
        log.setUpdatedOn(regionTypesRecord.getUpdatedOn());
        log.setIsActive(regionTypesRecord.getIsActive());
        log.setState(regionTypesRecord.getState());
        log.setApiSource(regionTypesRecord.getApiSource());
        log.setNotes(regionTypesRecord.getNotes());
        return log;
    }

    @Override
    public void delete(Integer id) {
     context.update(REGION_TYPES)
     .set(REGION_TYPES.STATE,RecordState.DELETED.name())
     .where(REGION_TYPES.ID.eq(id))
     .and(REGION_TYPES.STATE.eq(RecordState.ACTIVE.name()))
     .and(REGION_TYPES.IS_ACTIVE.eq(true))
     .execute();
    }

    @Override
    public RegionTypeDao findByUUID(String uuid) {
        return context.selectFrom(REGION_TYPES).where(REGION_TYPES.UUID.eq(uuid))
                .and(REGION_TYPES.IS_ACTIVE.eq(true))
                .fetchAnyInto(RegionTypeDao.class);
    }

    @Override
    public RegionTypeDao findById(Integer id) {
        return context.selectFrom(REGION_TYPES).where(REGION_TYPES.ID.eq(id))
                .and(REGION_TYPES.IS_ACTIVE.eq(true))
                .fetchAnyInto(RegionTypeDao.class);
    }

    @Override
    public List<RegionTypeDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(REGION_TYPES).where(REGION_TYPES.ID.in(ids)).fetchInto(RegionTypeDao.class);
    }

    @Override
    public List<RegionTypeDao> findAllActive() {
        return context.selectFrom(REGION_TYPES).fetchInto(RegionTypeDao.class);
    }
}