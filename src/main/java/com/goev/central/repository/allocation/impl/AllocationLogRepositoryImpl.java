package com.goev.central.repository.allocation.impl;

import com.goev.central.dao.allocation.AllocationLogDao;
import com.goev.central.repository.allocation.AllocationLogRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.AllocationLogsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.AllocationLogs.ALLOCATION_LOGS;

@Slf4j
@Repository
@AllArgsConstructor
public class AllocationLogRepositoryImpl implements AllocationLogRepository {

    private final DSLContext context;

    @Override
    public AllocationLogDao save(AllocationLogDao client) {
        AllocationLogsRecord allocationLogsRecord = context.newRecord(ALLOCATION_LOGS, client);
        allocationLogsRecord.store();
        client.setId(allocationLogsRecord.getId());
        client.setUuid(allocationLogsRecord.getUuid());
        return client;
    }

    @Override
    public AllocationLogDao update(AllocationLogDao client) {
        AllocationLogsRecord allocationLogsRecord = context.newRecord(ALLOCATION_LOGS, client);
        allocationLogsRecord.update();
        return client;
    }

    @Override
    public void delete(Integer id) {
        context.update(ALLOCATION_LOGS).set(ALLOCATION_LOGS.STATE, RecordState.DELETED.name()).where(ALLOCATION_LOGS.ID.eq(id)).execute();
    }

    @Override
    public AllocationLogDao findByUUID(String uuid) {
        return context.selectFrom(ALLOCATION_LOGS).where(ALLOCATION_LOGS.UUID.eq(uuid)).fetchAnyInto(AllocationLogDao.class);
    }

    @Override
    public AllocationLogDao findById(Integer id) {
        return context.selectFrom(ALLOCATION_LOGS).where(ALLOCATION_LOGS.ID.eq(id)).fetchAnyInto(AllocationLogDao.class);
    }

    @Override
    public List<AllocationLogDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(ALLOCATION_LOGS).where(ALLOCATION_LOGS.ID.in(ids)).fetchInto(AllocationLogDao.class);
    }

    @Override
    public List<AllocationLogDao> findAll() {
        return context.selectFrom(ALLOCATION_LOGS).fetchInto(AllocationLogDao.class);
    }
}
