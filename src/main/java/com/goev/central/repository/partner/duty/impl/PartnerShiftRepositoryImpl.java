package com.goev.central.repository.partner.duty.impl;

import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerShiftsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerShifts.PARTNER_SHIFTS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerShiftRepositoryImpl implements PartnerShiftRepository {

    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public PartnerShiftDao save(PartnerShiftDao partnerShiftDao) {
        PartnerShiftsRecord partnerShiftsRecord = context.newRecord(PARTNER_SHIFTS, partnerShiftDao);
        partnerShiftsRecord.store();
        partnerShiftDao.setId(partnerShiftsRecord.getId());
        partnerShiftDao.setUuid(partnerShiftsRecord.getUuid());
        partnerShiftDao.setCreatedBy(partnerShiftsRecord.getCreatedBy());
        partnerShiftDao.setUpdatedBy(partnerShiftsRecord.getUpdatedBy());
        partnerShiftDao.setCreatedOn(partnerShiftsRecord.getCreatedOn());
        partnerShiftDao.setUpdatedOn(partnerShiftsRecord.getUpdatedOn());
        partnerShiftDao.setIsActive(partnerShiftsRecord.getIsActive());
        partnerShiftDao.setState(partnerShiftsRecord.getState());
        partnerShiftDao.setApiSource(partnerShiftsRecord.getApiSource());
        partnerShiftDao.setNotes(partnerShiftsRecord.getNotes());

        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerShiftSaveEvent", partnerShiftDao);
        return partnerShiftDao;
    }

    @Override
    public PartnerShiftDao update(PartnerShiftDao partnerShiftDao) {
        PartnerShiftsRecord partnerShiftsRecord = context.newRecord(PARTNER_SHIFTS, partnerShiftDao);
        partnerShiftsRecord.update();


        partnerShiftDao.setCreatedBy(partnerShiftsRecord.getCreatedBy());
        partnerShiftDao.setUpdatedBy(partnerShiftsRecord.getUpdatedBy());
        partnerShiftDao.setCreatedOn(partnerShiftsRecord.getCreatedOn());
        partnerShiftDao.setUpdatedOn(partnerShiftsRecord.getUpdatedOn());
        partnerShiftDao.setIsActive(partnerShiftsRecord.getIsActive());
        partnerShiftDao.setState(partnerShiftsRecord.getState());
        partnerShiftDao.setApiSource(partnerShiftsRecord.getApiSource());
        partnerShiftDao.setNotes(partnerShiftsRecord.getNotes());
        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerShiftUpdateEvent", partnerShiftDao);
        return partnerShiftDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_SHIFTS)
                .set(PARTNER_SHIFTS.STATE, RecordState.DELETED.name())
                .where(PARTNER_SHIFTS.ID.eq(id))
                .and(PARTNER_SHIFTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_SHIFTS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerShiftDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_SHIFTS).where(PARTNER_SHIFTS.UUID.eq(uuid))
                .and(PARTNER_SHIFTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerShiftDao.class);
    }

    @Override
    public PartnerShiftDao findById(Integer id) {
        return context.selectFrom(PARTNER_SHIFTS).where(PARTNER_SHIFTS.ID.eq(id))
                .and(PARTNER_SHIFTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerShiftDao.class);
    }

    @Override
    public List<PartnerShiftDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_SHIFTS).where(PARTNER_SHIFTS.ID.in(ids)).fetchInto(PartnerShiftDao.class);
    }

    @Override
    public List<PartnerShiftDao> findAllActive() {
        return context.selectFrom(PARTNER_SHIFTS).fetchInto(PartnerShiftDao.class);
    }

    @Override
    public List<PartnerShiftDao> findAllByPartnerId(Integer id) {
        return context.selectFrom(PARTNER_SHIFTS).where(PARTNER_SHIFTS.PARTNER_ID.eq(id)).fetchInto(PartnerShiftDao.class);
    }

    @Override
    public List<PartnerShiftDao> findAllByStatus(String status, PageDto page) {
        return context.selectFrom(PARTNER_SHIFTS)
                .where(PARTNER_SHIFTS.STATUS.eq(status))
                .and(PARTNER_SHIFTS.IS_ACTIVE.eq(true))
                .limit(page.getLimit())
                .offset(page.getStart())
                .fetchInto(PartnerShiftDao.class);
    }

    @Override
    public PartnerShiftDao findByPartnerIdShiftIdDayDate(Integer partnerId, Integer shiftId, String currentDay, DateTime date) {
        return context.selectFrom(PARTNER_SHIFTS)
                .where(PARTNER_SHIFTS.PARTNER_ID.eq(partnerId))
                .and(PARTNER_SHIFTS.SHIFT_ID.eq(shiftId))
                .and(PARTNER_SHIFTS.DAY.eq(currentDay))
                .and(PARTNER_SHIFTS.DUTY_DATE.eq(date))
                .and(PARTNER_SHIFTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerShiftDao.class);
    }

    @Override
    public List<PartnerShiftDao> findAllByPartnerIdAndShiftIdAndStatus(Integer partnerId, Integer shiftId, String status) {
        return context.selectFrom(PARTNER_SHIFTS)
                .where(PARTNER_SHIFTS.PARTNER_ID.eq(partnerId))
                .and(PARTNER_SHIFTS.SHIFT_ID.eq(shiftId))
                .and(PARTNER_SHIFTS.STATUS.eq(status))
                .and(PARTNER_SHIFTS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerShiftDao.class);
    }
}
