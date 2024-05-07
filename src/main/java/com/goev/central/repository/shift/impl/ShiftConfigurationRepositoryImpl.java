package com.goev.central.repository.shift.impl;

import com.goev.central.dao.shift.ShiftConfigurationDao;
import com.goev.central.repository.shift.ShiftConfigurationRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.ShiftConfigurationsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.ShiftConfigurations.SHIFT_CONFIGURATIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class ShiftConfigurationRepositoryImpl implements ShiftConfigurationRepository {
    private final DSLContext context;

    @Override
    public ShiftConfigurationDao save(ShiftConfigurationDao log) {
        ShiftConfigurationsRecord shiftConfigurationsRecord = context.newRecord(SHIFT_CONFIGURATIONS, log);
        shiftConfigurationsRecord.store();
        log.setId(shiftConfigurationsRecord.getId());
        log.setUuid(shiftConfigurationsRecord.getUuid());
        return log;
    }

    @Override
    public ShiftConfigurationDao update(ShiftConfigurationDao log) {
        ShiftConfigurationsRecord shiftConfigurationsRecord = context.newRecord(SHIFT_CONFIGURATIONS, log);
        shiftConfigurationsRecord.update();
        return log;
    }

    @Override
    public void delete(Integer id) {
        context.update(SHIFT_CONFIGURATIONS).set(SHIFT_CONFIGURATIONS.STATE, RecordState.DELETED.name()).where(SHIFT_CONFIGURATIONS.ID.eq(id)).execute();
    }

    @Override
    public ShiftConfigurationDao findByUUID(String uuid) {
        return context.selectFrom(SHIFT_CONFIGURATIONS).where(SHIFT_CONFIGURATIONS.UUID.eq(uuid)).fetchAnyInto(ShiftConfigurationDao.class);
    }

    @Override
    public ShiftConfigurationDao findById(Integer id) {
        return context.selectFrom(SHIFT_CONFIGURATIONS).where(SHIFT_CONFIGURATIONS.ID.eq(id)).fetchAnyInto(ShiftConfigurationDao.class);
    }

    @Override
    public List<ShiftConfigurationDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(SHIFT_CONFIGURATIONS).where(SHIFT_CONFIGURATIONS.ID.in(ids)).fetchInto(ShiftConfigurationDao.class);
    }

    @Override
    public List<ShiftConfigurationDao> findAll() {
        return context.selectFrom(SHIFT_CONFIGURATIONS).fetchInto(ShiftConfigurationDao.class);
    }
}