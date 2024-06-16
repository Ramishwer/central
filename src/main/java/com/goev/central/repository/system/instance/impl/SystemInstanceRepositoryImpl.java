package com.goev.central.repository.system.instance.impl;

import com.goev.central.dao.system.instance.SystemInstanceDao;
import com.goev.central.repository.system.instance.SystemInstanceRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.SystemInstancesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.SystemInstances.SYSTEM_INSTANCES;

@Slf4j
@Repository
@AllArgsConstructor
public class SystemInstanceRepositoryImpl implements SystemInstanceRepository {
    private final DSLContext context;

    @Override
    public SystemInstanceDao save(SystemInstanceDao instance) {
        SystemInstancesRecord systemInstancesRecord = context.newRecord(SYSTEM_INSTANCES, instance);
        systemInstancesRecord.store();
        instance.setId(systemInstancesRecord.getId());
        instance.setUuid(systemInstancesRecord.getUuid());
        instance.setCreatedBy(systemInstancesRecord.getCreatedBy());
        instance.setUpdatedBy(systemInstancesRecord.getUpdatedBy());
        instance.setCreatedOn(systemInstancesRecord.getCreatedOn());
        instance.setUpdatedOn(systemInstancesRecord.getUpdatedOn());
        instance.setIsActive(systemInstancesRecord.getIsActive());
        instance.setState(systemInstancesRecord.getState());
        instance.setApiSource(systemInstancesRecord.getApiSource());
        instance.setNotes(systemInstancesRecord.getNotes());
        return instance;
    }

    @Override
    public SystemInstanceDao update(SystemInstanceDao instance) {
        SystemInstancesRecord systemInstancesRecord = context.newRecord(SYSTEM_INSTANCES, instance);
        systemInstancesRecord.update();


        instance.setCreatedBy(systemInstancesRecord.getCreatedBy());
        instance.setUpdatedBy(systemInstancesRecord.getUpdatedBy());
        instance.setCreatedOn(systemInstancesRecord.getCreatedOn());
        instance.setUpdatedOn(systemInstancesRecord.getUpdatedOn());
        instance.setIsActive(systemInstancesRecord.getIsActive());
        instance.setState(systemInstancesRecord.getState());
        instance.setApiSource(systemInstancesRecord.getApiSource());
        instance.setNotes(systemInstancesRecord.getNotes());
        return instance;
    }

    @Override
    public void delete(Integer id) {
        context.update(SYSTEM_INSTANCES)
                .set(SYSTEM_INSTANCES.STATE, RecordState.DELETED.name())
                .where(SYSTEM_INSTANCES.ID.eq(id))
                .and(SYSTEM_INSTANCES.STATE.eq(RecordState.ACTIVE.name()))
                .and(SYSTEM_INSTANCES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public SystemInstanceDao findByUUID(String uuid) {
        return context.selectFrom(SYSTEM_INSTANCES).where(SYSTEM_INSTANCES.UUID.eq(uuid))
                .and(SYSTEM_INSTANCES.IS_ACTIVE.eq(true))
                .fetchAnyInto(SystemInstanceDao.class);
    }

    @Override
    public SystemInstanceDao findById(Integer id) {
        return context.selectFrom(SYSTEM_INSTANCES).where(SYSTEM_INSTANCES.ID.eq(id))
                .and(SYSTEM_INSTANCES.IS_ACTIVE.eq(true))
                .fetchAnyInto(SystemInstanceDao.class);
    }

    @Override
    public List<SystemInstanceDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(SYSTEM_INSTANCES).where(SYSTEM_INSTANCES.ID.in(ids)).fetchInto(SystemInstanceDao.class);
    }

    @Override
    public List<SystemInstanceDao> findAllActive() {
        return context.selectFrom(SYSTEM_INSTANCES).fetchInto(SystemInstanceDao.class);
    }
}