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
        log.setCreatedBy(shiftsRecord.getCreatedBy());
        log.setUpdatedBy(shiftsRecord.getUpdatedBy());
        log.setCreatedOn(shiftsRecord.getCreatedOn());
        log.setUpdatedOn(shiftsRecord.getUpdatedOn());
        log.setIsActive(shiftsRecord.getIsActive());
        log.setState(shiftsRecord.getState());
        log.setApiSource(shiftsRecord.getApiSource());
        log.setNotes(shiftsRecord.getNotes());
        return log;
    }

    @Override
    public ShiftDao update(ShiftDao log) {
        ShiftsRecord shiftsRecord = context.newRecord(SHIFTS, log);
        shiftsRecord.update();


        log.setCreatedBy(shiftsRecord.getCreatedBy());
        log.setUpdatedBy(shiftsRecord.getUpdatedBy());
        log.setCreatedOn(shiftsRecord.getCreatedOn());
        log.setUpdatedOn(shiftsRecord.getUpdatedOn());
        log.setIsActive(shiftsRecord.getIsActive());
        log.setState(shiftsRecord.getState());
        log.setApiSource(shiftsRecord.getApiSource());
        log.setNotes(shiftsRecord.getNotes());
        return log;
    }

    @Override
    public void delete(Integer id) {
     context.update(SHIFTS)
     .set(SHIFTS.STATE,RecordState.DELETED.name())
     .where(SHIFTS.ID.eq(id))
     .and(SHIFTS.STATE.eq(RecordState.ACTIVE.name()))
     .and(SHIFTS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public ShiftDao findByUUID(String uuid) {
        return context.selectFrom(SHIFTS).where(SHIFTS.UUID.eq(uuid))
                .and(SHIFTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(ShiftDao.class);
    }

    @Override
    public ShiftDao findById(Integer id) {
        return context.selectFrom(SHIFTS).where(SHIFTS.ID.eq(id))
                .and(SHIFTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(ShiftDao.class);
    }

    @Override
    public List<ShiftDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(SHIFTS).where(SHIFTS.ID.in(ids)).fetchInto(ShiftDao.class);
    }

    @Override
    public List<ShiftDao> findAllActive() {
        return context.selectFrom(SHIFTS).fetchInto(ShiftDao.class);
    }
}