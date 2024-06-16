package com.goev.central.repository.region.impl;

import com.goev.central.dao.region.RegionDao;
import com.goev.central.repository.region.RegionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.RegionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Regions.REGIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class RegionRepositoryImpl implements RegionRepository {
    private final DSLContext context;

    @Override
    public RegionDao save(RegionDao log) {
        RegionsRecord regionsRecord = context.newRecord(REGIONS, log);
        regionsRecord.store();
        log.setId(regionsRecord.getId());
        log.setUuid(regionsRecord.getUuid());
        log.setCreatedBy(regionsRecord.getCreatedBy());
        log.setUpdatedBy(regionsRecord.getUpdatedBy());
        log.setCreatedOn(regionsRecord.getCreatedOn());
        log.setUpdatedOn(regionsRecord.getUpdatedOn());
        log.setIsActive(regionsRecord.getIsActive());
        log.setState(regionsRecord.getState());
        log.setApiSource(regionsRecord.getApiSource());
        log.setNotes(regionsRecord.getNotes());
        return log;
    }

    @Override
    public RegionDao update(RegionDao log) {
        RegionsRecord regionsRecord = context.newRecord(REGIONS, log);
        regionsRecord.update();


        log.setCreatedBy(regionsRecord.getCreatedBy());
        log.setUpdatedBy(regionsRecord.getUpdatedBy());
        log.setCreatedOn(regionsRecord.getCreatedOn());
        log.setUpdatedOn(regionsRecord.getUpdatedOn());
        log.setIsActive(regionsRecord.getIsActive());
        log.setState(regionsRecord.getState());
        log.setApiSource(regionsRecord.getApiSource());
        log.setNotes(regionsRecord.getNotes());
        return log;
    }

    @Override
    public void delete(Integer id) {
     context.update(REGIONS)
     .set(REGIONS.STATE,RecordState.DELETED.name())
     .where(REGIONS.ID.eq(id))
     .and(REGIONS.STATE.eq(RecordState.ACTIVE.name()))
     .and(REGIONS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public RegionDao findByUUID(String uuid) {
        return context.selectFrom(REGIONS).where(REGIONS.UUID.eq(uuid))
                .and(REGIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(RegionDao.class);
    }

    @Override
    public RegionDao findById(Integer id) {
        return context.selectFrom(REGIONS).where(REGIONS.ID.eq(id))
                .and(REGIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(RegionDao.class);
    }

    @Override
    public List<RegionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(REGIONS).where(REGIONS.ID.in(ids)).fetchInto(RegionDao.class);
    }

    @Override
    public List<RegionDao> findAllActive() {
        return context.selectFrom(REGIONS).fetchInto(RegionDao.class);
    }
}