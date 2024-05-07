package com.goev.central.repository.allocation.impl;

import com.goev.central.dao.allocation.AllocationResultDao;
import com.goev.central.repository.allocation.AllocationResultRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.AllocationResultsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.AllocationResults.ALLOCATION_RESULTS;

@Slf4j
@Repository
@AllArgsConstructor
public class AllocationResultRepositoryImpl implements AllocationResultRepository {

    private final DSLContext context;

    @Override
    public AllocationResultDao save(AllocationResultDao client) {
        AllocationResultsRecord allocationResultsRecord = context.newRecord(ALLOCATION_RESULTS, client);
        allocationResultsRecord.store();
        client.setId(allocationResultsRecord.getId());
        client.setUuid(allocationResultsRecord.getUuid());
        return client;
    }

    @Override
    public AllocationResultDao update(AllocationResultDao client) {
        AllocationResultsRecord allocationResultsRecord = context.newRecord(ALLOCATION_RESULTS, client);
        allocationResultsRecord.update();
        return client;
    }

    @Override
    public void delete(Integer id) {
        context.update(ALLOCATION_RESULTS).set(ALLOCATION_RESULTS.STATE, RecordState.DELETED.name()).where(ALLOCATION_RESULTS.ID.eq(id)).execute();
    }

    @Override
    public AllocationResultDao findByUUID(String uuid) {
        return context.selectFrom(ALLOCATION_RESULTS).where(ALLOCATION_RESULTS.UUID.eq(uuid)).fetchAnyInto(AllocationResultDao.class);
    }

    @Override
    public AllocationResultDao findById(Integer id) {
        return context.selectFrom(ALLOCATION_RESULTS).where(ALLOCATION_RESULTS.ID.eq(id)).fetchAnyInto(AllocationResultDao.class);
    }

    @Override
    public List<AllocationResultDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(ALLOCATION_RESULTS).where(ALLOCATION_RESULTS.ID.in(ids)).fetchInto(AllocationResultDao.class);
    }

    @Override
    public List<AllocationResultDao> findAll() {
        return context.selectFrom(ALLOCATION_RESULTS).fetchInto(AllocationResultDao.class);
    }
}
