package com.goev.central.repository.partner.duty.impl;

import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.repository.partner.duty.PartnerDutyRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerDutiesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerDuties.PARTNER_DUTIES;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerDutyRepositoryImpl implements PartnerDutyRepository {

    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public PartnerDutyDao save(PartnerDutyDao partnerDutyDao) {
        PartnerDutiesRecord partnerDutiesRecord = context.newRecord(PARTNER_DUTIES, partnerDutyDao);
        partnerDutiesRecord.store();
        partnerDutyDao.setId(partnerDutiesRecord.getId());
        partnerDutyDao.setUuid(partnerDutiesRecord.getUuid());
        partnerDutyDao.setCreatedBy(partnerDutiesRecord.getCreatedBy());
        partnerDutyDao.setUpdatedBy(partnerDutiesRecord.getUpdatedBy());
        partnerDutyDao.setCreatedOn(partnerDutiesRecord.getCreatedOn());
        partnerDutyDao.setUpdatedOn(partnerDutiesRecord.getUpdatedOn());
        partnerDutyDao.setIsActive(partnerDutiesRecord.getIsActive());
        partnerDutyDao.setState(partnerDutiesRecord.getState());
        partnerDutyDao.setApiSource(partnerDutiesRecord.getApiSource());
        partnerDutyDao.setNotes(partnerDutiesRecord.getNotes());

        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerDutySaveEvent", partnerDutyDao);
        return partnerDutyDao;
    }

    @Override
    public PartnerDutyDao update(PartnerDutyDao partnerDutyDao) {
        PartnerDutiesRecord partnerDutiesRecord = context.newRecord(PARTNER_DUTIES, partnerDutyDao);
        partnerDutiesRecord.update();


        partnerDutyDao.setCreatedBy(partnerDutiesRecord.getCreatedBy());
        partnerDutyDao.setUpdatedBy(partnerDutiesRecord.getUpdatedBy());
        partnerDutyDao.setCreatedOn(partnerDutiesRecord.getCreatedOn());
        partnerDutyDao.setUpdatedOn(partnerDutiesRecord.getUpdatedOn());
        partnerDutyDao.setIsActive(partnerDutiesRecord.getIsActive());
        partnerDutyDao.setState(partnerDutiesRecord.getState());
        partnerDutyDao.setApiSource(partnerDutiesRecord.getApiSource());
        partnerDutyDao.setNotes(partnerDutiesRecord.getNotes());
        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerDutyUpdateEvent", partnerDutyDao);
        return partnerDutyDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_DUTIES)
                .set(PARTNER_DUTIES.STATE, RecordState.DELETED.name())
                .where(PARTNER_DUTIES.ID.eq(id))
                .and(PARTNER_DUTIES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_DUTIES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerDutyDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_DUTIES).where(PARTNER_DUTIES.UUID.eq(uuid))
                .and(PARTNER_DUTIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDutyDao.class);
    }

    @Override
    public PartnerDutyDao findById(Integer id) {
        return context.selectFrom(PARTNER_DUTIES).where(PARTNER_DUTIES.ID.eq(id))
                .and(PARTNER_DUTIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDutyDao.class);
    }

    @Override
    public List<PartnerDutyDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_DUTIES).where(PARTNER_DUTIES.ID.in(ids)).fetchInto(PartnerDutyDao.class);
    }

    @Override
    public List<PartnerDutyDao> findAllActive() {
        return context.selectFrom(PARTNER_DUTIES).fetchInto(PartnerDutyDao.class);
    }


    @Override
    public List<PartnerDutyDao> findAllByPartnerId(Integer id) {
        return context.selectFrom(PARTNER_DUTIES).where(PARTNER_DUTIES.PARTNER_ID.eq(id)).fetchInto(PartnerDutyDao.class);
    }

    @Override
    public List<PartnerDutyDao> findAllByStatus(String status, PageDto page, FilterDto filter) {
        return context.selectFrom(PARTNER_DUTIES).where(PARTNER_DUTIES.STATUS.eq(status))
                .and(PARTNER_DUTIES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_DUTIES.IS_ACTIVE.eq(true))
                .and(PARTNER_DUTIES.ACTUAL_DUTY_START_TIME.between(filter.getStartTime(),filter.getEndTime()))
                .limit(page.getLimit())
                .offset(page.getStart())
                .fetchInto(PartnerDutyDao.class);
    }

    @Override
    public List<PartnerDutyDao> findAllByStatus(String status, PageDto page) {
        return context.selectFrom(PARTNER_DUTIES).where(PARTNER_DUTIES.STATUS.eq(status))
                .and(PARTNER_DUTIES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_DUTIES.IS_ACTIVE.eq(true))
                .limit(page.getLimit())
                .offset(page.getStart())
                .fetchInto(PartnerDutyDao.class);
    }

    @Override
    public List<PartnerDutyDao> findAllByPartnerIdAndPartnerShiftIdsAndStatus(Integer partnerId, List<Integer> partnerShiftIds,String status) {
        return context.selectFrom(PARTNER_DUTIES)
                .where(PARTNER_DUTIES.PARTNER_ID.eq(partnerId))
                .and(PARTNER_DUTIES.PARTNER_SHIFT_ID.in(partnerShiftIds))
                .and(PARTNER_DUTIES.STATUS.eq(status))
                .and(PARTNER_DUTIES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_DUTIES.IS_ACTIVE.eq(true))
                .fetchInto(PartnerDutyDao.class);
    }
}
