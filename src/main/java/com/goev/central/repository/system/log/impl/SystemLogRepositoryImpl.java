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
        log.setCreatedBy(systemLogsRecord.getCreatedBy());
        log.setUpdatedBy(systemLogsRecord.getUpdatedBy());
        log.setCreatedOn(systemLogsRecord.getCreatedOn());
        log.setUpdatedOn(systemLogsRecord.getUpdatedOn());
        log.setIsActive(systemLogsRecord.getIsActive());
        log.setState(systemLogsRecord.getState());
        log.setApiSource(systemLogsRecord.getApiSource());
        log.setNotes(systemLogsRecord.getNotes());
        return log;
    }

    @Override
    public SystemLogDao update(SystemLogDao log) {
        SystemLogsRecord systemLogsRecord = context.newRecord(SYSTEM_LOGS, log);
        systemLogsRecord.update();


        log.setCreatedBy(systemLogsRecord.getCreatedBy());
        log.setUpdatedBy(systemLogsRecord.getUpdatedBy());
        log.setCreatedOn(systemLogsRecord.getCreatedOn());
        log.setUpdatedOn(systemLogsRecord.getUpdatedOn());
        log.setIsActive(systemLogsRecord.getIsActive());
        log.setState(systemLogsRecord.getState());
        log.setApiSource(systemLogsRecord.getApiSource());
        log.setNotes(systemLogsRecord.getNotes());
        return log;
    }

    @Override
    public void delete(Integer id) {
     context.update(SYSTEM_LOGS)
     .set(SYSTEM_LOGS.STATE,RecordState.DELETED.name())
     .where(SYSTEM_LOGS.ID.eq(id))
     .and(SYSTEM_LOGS.STATE.eq(RecordState.ACTIVE.name()))
     .and(SYSTEM_LOGS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public SystemLogDao findByUUID(String uuid) {
        return context.selectFrom(SYSTEM_LOGS).where(SYSTEM_LOGS.UUID.eq(uuid))
                .and(SYSTEM_LOGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(SystemLogDao.class);
    }

    @Override
    public SystemLogDao findById(Integer id) {
        return context.selectFrom(SYSTEM_LOGS).where(SYSTEM_LOGS.ID.eq(id))
                .and(SYSTEM_LOGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(SystemLogDao.class);
    }

    @Override
    public List<SystemLogDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(SYSTEM_LOGS).where(SYSTEM_LOGS.ID.in(ids)).fetchInto(SystemLogDao.class);
    }

    @Override
    public List<SystemLogDao> findAllActive() {
        return context.selectFrom(SYSTEM_LOGS).fetchInto(SystemLogDao.class);
    }
}