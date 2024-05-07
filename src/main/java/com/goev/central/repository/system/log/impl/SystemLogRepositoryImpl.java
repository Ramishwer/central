package com.goev.central.repository.system.log.impl;

import com.goev.central.dao.system.log.SystemLogDao;
import com.goev.central.repository.system.log.SystemLogRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.SystemLogsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.goev.record.central.tables.SystemLogs.SYSTEM_LOGS;

@Slf4j
@Repository
@AllArgsConstructor
public class SystemLogRepositoryImpl implements SystemLogRepository {
    private final DSLContext context;

    @Override
    public SystemLogDao save(SystemLogDao log) {
        SystemLogsRecord systemLogsRecord = context.newRecord(SYSTEM_LOGS, log);
        systemLogsRecord.store();
        log.setId(systemLogsRecord.getId());
        log.setUuid(systemLogsRecord.getUuid());
        return log;
    }

    @Override
    public SystemLogDao update(SystemLogDao log) {
        SystemLogsRecord systemLogsRecord = context.newRecord(SYSTEM_LOGS, log);
        systemLogsRecord.update();
        return log;
    }

    @Override
    public void delete(Integer id) {
        context.update(SYSTEM_LOGS).set(SYSTEM_LOGS.STATE, RecordState.DELETED.name()).where(SYSTEM_LOGS.ID.eq(id)).execute();
    }

    @Override
    public SystemLogDao findByUUID(String uuid) {
        return context.selectFrom(SYSTEM_LOGS).where(SYSTEM_LOGS.UUID.eq(uuid)).fetchAnyInto(SystemLogDao.class);
    }

    @Override
    public SystemLogDao findById(Integer id) {
        return context.selectFrom(SYSTEM_LOGS).where(SYSTEM_LOGS.ID.eq(id)).fetchAnyInto(SystemLogDao.class);
    }

    @Override
    public List<SystemLogDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(SYSTEM_LOGS).where(SYSTEM_LOGS.ID.in(ids)).fetchInto(SystemLogDao.class);
    }

    @Override
    public List<SystemLogDao> findAll() {
        return context.selectFrom(SYSTEM_LOGS).fetchInto(SystemLogDao.class);
    }
}