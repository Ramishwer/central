package com.goev.central.repository.partner.payout.impl;

import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dao.partner.payout.PartnerPayoutDao;
import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.repository.partner.payout.PartnerPayoutRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerPayoutsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerPayouts.PARTNER_PAYOUTS;
import static com.goev.record.central.tables.PartnerShifts.PARTNER_SHIFTS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerPayoutRepositoryImpl implements PartnerPayoutRepository {

    private final DSLContext context;

    @Override
    public PartnerPayoutDao save(PartnerPayoutDao partnerPayout) {
        PartnerPayoutsRecord partnerPayoutsRecord = context.newRecord(PARTNER_PAYOUTS, partnerPayout);
        partnerPayoutsRecord.store();
        partnerPayout.setId(partnerPayoutsRecord.getId());
        partnerPayout.setUuid(partnerPayoutsRecord.getUuid());
        partnerPayout.setCreatedBy(partnerPayoutsRecord.getCreatedBy());
        partnerPayout.setUpdatedBy(partnerPayoutsRecord.getUpdatedBy());
        partnerPayout.setCreatedOn(partnerPayoutsRecord.getCreatedOn());
        partnerPayout.setUpdatedOn(partnerPayoutsRecord.getUpdatedOn());
        partnerPayout.setIsActive(partnerPayoutsRecord.getIsActive());
        partnerPayout.setState(partnerPayoutsRecord.getState());
        partnerPayout.setApiSource(partnerPayoutsRecord.getApiSource());
        partnerPayout.setNotes(partnerPayoutsRecord.getNotes());
        return partnerPayout;
    }

    @Override
    public PartnerPayoutDao update(PartnerPayoutDao partnerPayout) {
        PartnerPayoutsRecord partnerPayoutsRecord = context.newRecord(PARTNER_PAYOUTS, partnerPayout);
        partnerPayoutsRecord.update();


        partnerPayout.setCreatedBy(partnerPayoutsRecord.getCreatedBy());
        partnerPayout.setUpdatedBy(partnerPayoutsRecord.getUpdatedBy());
        partnerPayout.setCreatedOn(partnerPayoutsRecord.getCreatedOn());
        partnerPayout.setUpdatedOn(partnerPayoutsRecord.getUpdatedOn());
        partnerPayout.setIsActive(partnerPayoutsRecord.getIsActive());
        partnerPayout.setState(partnerPayoutsRecord.getState());
        partnerPayout.setApiSource(partnerPayoutsRecord.getApiSource());
        partnerPayout.setNotes(partnerPayoutsRecord.getNotes());
        return partnerPayout;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_PAYOUTS)
                .set(PARTNER_PAYOUTS.STATE, RecordState.DELETED.name())
                .where(PARTNER_PAYOUTS.ID.eq(id))
                .and(PARTNER_PAYOUTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_PAYOUTS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerPayoutDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_PAYOUTS).where(PARTNER_PAYOUTS.UUID.eq(uuid))
                .and(PARTNER_PAYOUTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerPayoutDao.class);
    }

    @Override
    public PartnerPayoutDao findById(Integer id) {
        return context.selectFrom(PARTNER_PAYOUTS).where(PARTNER_PAYOUTS.ID.eq(id))
                .and(PARTNER_PAYOUTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerPayoutDao.class);
    }

    @Override
    public List<PartnerPayoutDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_PAYOUTS).where(PARTNER_PAYOUTS.ID.in(ids)).fetchInto(PartnerPayoutDao.class);
    }

    @Override
    public List<PartnerPayoutDao> findAllActive() {
        return context.selectFrom(PARTNER_PAYOUTS).fetchInto(PartnerPayoutDao.class);
    }

    @Override
    public List<PartnerPayoutDao> findAllByPartnerId(Integer id) {
        return context.selectFrom(PARTNER_PAYOUTS).where(PARTNER_PAYOUTS.PARTNER_ID.eq(id)).fetchInto(PartnerPayoutDao.class);
    }

    @Override
    public List<PartnerPayoutDao> findAllByStatus(String status, PageDto page, FilterDto filter) {
        return context.selectFrom(PARTNER_PAYOUTS)
                .where(PARTNER_PAYOUTS.PAYOUT_START_DATE.between(filter.getStartTime(), filter.getEndTime()))
                .and(PARTNER_PAYOUTS.STATUS.eq(status))
                .and(PARTNER_PAYOUTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_PAYOUTS.IS_ACTIVE.eq(true))
                .limit(page.getLimit())
                .offset(page.getStart())
                .fetchInto(PartnerPayoutDao.class);
    }

    @Override
    public List<PartnerPayoutDao> findAllByStatus(String status, PageDto page) {
        return context.selectFrom(PARTNER_PAYOUTS)
                .where(PARTNER_PAYOUTS.STATUS.eq(status))
                .and(PARTNER_PAYOUTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_PAYOUTS.IS_ACTIVE.eq(true))
                .limit(page.getLimit())
                .offset(page.getStart())
                .fetchInto(PartnerPayoutDao.class);
    }

    @Override
    public PartnerPayoutDao findByPartnerIdAndStartAndEndTime(Integer partnerId, DateTime start, DateTime end) {
        return context.selectFrom(PARTNER_PAYOUTS)
                .where(PARTNER_PAYOUTS.PARTNER_ID.eq(partnerId))
                .and(PARTNER_PAYOUTS.PAYOUT_START_DATE.eq(start))
                .and(PARTNER_PAYOUTS.PAYOUT_END_DATE.eq(end))
                .and(PARTNER_PAYOUTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_PAYOUTS.IS_ACTIVE.eq(true))
                .fetchOneInto(PartnerPayoutDao.class);
    }
}
