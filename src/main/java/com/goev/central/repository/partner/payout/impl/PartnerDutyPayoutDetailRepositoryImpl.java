package com.goev.central.repository.partner.payout.impl;

import com.goev.central.dao.partner.payout.PartnerDutyPayoutDetailDao;
import com.goev.central.repository.partner.payout.PartnerDutyPayoutDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerDutyPayoutDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerDutyPayoutDetails.PARTNER_DUTY_PAYOUT_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerDutyPayoutDetailRepositoryImpl implements PartnerDutyPayoutDetailRepository {

    private final DSLContext context;

    @Override
    public PartnerDutyPayoutDetailDao save(PartnerDutyPayoutDetailDao partnerDutyPayoutDetail) {
        PartnerDutyPayoutDetailsRecord partnerDutyPayoutDetailsRecord = context.newRecord(PARTNER_DUTY_PAYOUT_DETAILS, partnerDutyPayoutDetail);
        partnerDutyPayoutDetailsRecord.store();
        partnerDutyPayoutDetail.setId(partnerDutyPayoutDetailsRecord.getId());
        partnerDutyPayoutDetail.setUuid(partnerDutyPayoutDetail.getUuid());
        partnerDutyPayoutDetail.setCreatedBy(partnerDutyPayoutDetail.getCreatedBy());
        partnerDutyPayoutDetail.setUpdatedBy(partnerDutyPayoutDetail.getUpdatedBy());
        partnerDutyPayoutDetail.setCreatedOn(partnerDutyPayoutDetail.getCreatedOn());
        partnerDutyPayoutDetail.setUpdatedOn(partnerDutyPayoutDetail.getUpdatedOn());
        partnerDutyPayoutDetail.setIsActive(partnerDutyPayoutDetail.getIsActive());
        partnerDutyPayoutDetail.setState(partnerDutyPayoutDetail.getState());
        partnerDutyPayoutDetail.setApiSource(partnerDutyPayoutDetail.getApiSource());
        partnerDutyPayoutDetail.setNotes(partnerDutyPayoutDetail.getNotes());
        return partnerDutyPayoutDetail;
    }

    @Override
    public PartnerDutyPayoutDetailDao update(PartnerDutyPayoutDetailDao partnerDutyPayoutDetail) {
        PartnerDutyPayoutDetailsRecord partnerDutyPayoutDetailsRecord = context.newRecord(PARTNER_DUTY_PAYOUT_DETAILS, partnerDutyPayoutDetail);
        partnerDutyPayoutDetailsRecord.update();


        partnerDutyPayoutDetail.setCreatedBy(partnerDutyPayoutDetailsRecord.getCreatedBy());
        partnerDutyPayoutDetail.setUpdatedBy(partnerDutyPayoutDetailsRecord.getUpdatedBy());
        partnerDutyPayoutDetail.setCreatedOn(partnerDutyPayoutDetailsRecord.getCreatedOn());
        partnerDutyPayoutDetail.setUpdatedOn(partnerDutyPayoutDetailsRecord.getUpdatedOn());
        partnerDutyPayoutDetail.setIsActive(partnerDutyPayoutDetailsRecord.getIsActive());
        partnerDutyPayoutDetail.setState(partnerDutyPayoutDetailsRecord.getState());
        partnerDutyPayoutDetail.setApiSource(partnerDutyPayoutDetailsRecord.getApiSource());
        partnerDutyPayoutDetail.setNotes(partnerDutyPayoutDetailsRecord.getNotes());
        return partnerDutyPayoutDetail;
    }

    @Override
    public void delete(Integer id) {
     context.update(PARTNER_DUTY_PAYOUT_DETAILS)
     .set(PARTNER_DUTY_PAYOUT_DETAILS.STATE,RecordState.DELETED.name())
     .where(PARTNER_DUTY_PAYOUT_DETAILS.ID.eq(id))
     .and(PARTNER_DUTY_PAYOUT_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
     .and(PARTNER_DUTY_PAYOUT_DETAILS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public PartnerDutyPayoutDetailDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_DUTY_PAYOUT_DETAILS).where(PARTNER_DUTY_PAYOUT_DETAILS.UUID.eq(uuid))
                .and(PARTNER_DUTY_PAYOUT_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDutyPayoutDetailDao.class);
    }

    @Override
    public PartnerDutyPayoutDetailDao findById(Integer id) {
        return context.selectFrom(PARTNER_DUTY_PAYOUT_DETAILS).where(PARTNER_DUTY_PAYOUT_DETAILS.ID.eq(id))
                .and(PARTNER_DUTY_PAYOUT_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDutyPayoutDetailDao.class);
    }

    @Override
    public List<PartnerDutyPayoutDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_DUTY_PAYOUT_DETAILS).where(PARTNER_DUTY_PAYOUT_DETAILS.ID.in(ids)).fetchInto(PartnerDutyPayoutDetailDao.class);
    }

    @Override
    public List<PartnerDutyPayoutDetailDao> findAllActive() {
        return context.selectFrom(PARTNER_DUTY_PAYOUT_DETAILS).fetchInto(PartnerDutyPayoutDetailDao.class);
    }
}
