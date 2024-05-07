package com.goev.central.repository.shift.impl;

import com.goev.central.dao.shift.ShiftDao;
import com.goev.central.repository.shift.ShiftRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.ShiftsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Shifts.SHIFTS;

@Slf4j
@Repository
@AllArgsConstructor
public class ShiftRepositoryImpl implements ShiftRepository {
    private final DSLContext context;

    @Override
    public ShiftDao save(ShiftDao log) {
        ShiftsRecord shiftsRecord = context.newRecord(SHIFTS, log);
        shiftsRecord.store();
        log.setId(shiftsRecord.getId());
        log.setUuid(shiftsRecord.getUuid());
        return log;
    }

    @Override
    public ShiftDao update(ShiftDao log) {
        ShiftsRecord shiftsRecord = context.newRecord(SHIFTS, log);
        shiftsRecord.update();
        return log;
    }

    @Override
    public void delete(Integer id) {
        context.update(SHIFTS).set(SHIFTS.STATE, RecordState.DELETED.name()).where(SHIFTS.ID.eq(id)).execute();
    }

    @Override
    public ShiftDao findByUUID(String uuid) {
        return context.selectFrom(SHIFTS).where(SHIFTS.UUID.eq(uuid)).fetchAnyInto(ShiftDao.class);
    }

    @Override
    public ShiftDao findById(Integer id) {
        return context.selectFrom(SHIFTS).where(SHIFTS.ID.eq(id)).fetchAnyInto(ShiftDao.class);
    }

    @Override
    public List<ShiftDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(SHIFTS).where(SHIFTS.ID.in(ids)).fetchInto(ShiftDao.class);
    }

    @Override
    public List<ShiftDao> findAll() {
        return context.selectFrom(SHIFTS).fetchInto(ShiftDao.class);
    }
}