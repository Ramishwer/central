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
import java.util.Map;

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
        return log;
    }

    @Override
    public RegionDao update(RegionDao log) {
        RegionsRecord regionsRecord = context.newRecord(REGIONS, log);
        regionsRecord.update();
        return log;
    }

    @Override
    public void delete(Integer id) {
        context.update(REGIONS).set(REGIONS.STATE, RecordState.DELETED.name()).where(REGIONS.ID.eq(id)).execute();
    }

    @Override
    public RegionDao findByUUID(String uuid) {
        return context.selectFrom(REGIONS).where(REGIONS.UUID.eq(uuid)).fetchAnyInto(RegionDao.class);
    }

    @Override
    public RegionDao findById(Integer id) {
        return context.selectFrom(REGIONS).where(REGIONS.ID.eq(id)).fetchAnyInto(RegionDao.class);
    }

    @Override
    public List<RegionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(REGIONS).where(REGIONS.ID.in(ids)).fetchInto(RegionDao.class);
    }

    @Override
    public List<RegionDao> findAll() {
        return context.selectFrom(REGIONS).fetchInto(RegionDao.class);
    }
}