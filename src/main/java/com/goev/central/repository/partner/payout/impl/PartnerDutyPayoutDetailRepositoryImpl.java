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
        return partnerDutyPayoutDetail;
    }

    @Override
    public PartnerDutyPayoutDetailDao update(PartnerDutyPayoutDetailDao partnerDutyPayoutDetail) {
        PartnerDutyPayoutDetailsRecord partnerDutyPayoutDetailsRecord = context.newRecord(PARTNER_DUTY_PAYOUT_DETAILS, partnerDutyPayoutDetail);
        partnerDutyPayoutDetailsRecord.update();
        return partnerDutyPayoutDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_DUTY_PAYOUT_DETAILS).set(PARTNER_DUTY_PAYOUT_DETAILS.STATE, RecordState.DELETED.name()).where(PARTNER_DUTY_PAYOUT_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public PartnerDutyPayoutDetailDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_DUTY_PAYOUT_DETAILS).where(PARTNER_DUTY_PAYOUT_DETAILS.UUID.eq(uuid)).fetchAnyInto(PartnerDutyPayoutDetailDao.class);
    }

    @Override
    public PartnerDutyPayoutDetailDao findById(Integer id) {
        return context.selectFrom(PARTNER_DUTY_PAYOUT_DETAILS).where(PARTNER_DUTY_PAYOUT_DETAILS.ID.eq(id)).fetchAnyInto(PartnerDutyPayoutDetailDao.class);
    }

    @Override
    public List<PartnerDutyPayoutDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_DUTY_PAYOUT_DETAILS).where(PARTNER_DUTY_PAYOUT_DETAILS.ID.in(ids)).fetchInto(PartnerDutyPayoutDetailDao.class);
    }

    @Override
    public List<PartnerDutyPayoutDetailDao> findAll() {
        return context.selectFrom(PARTNER_DUTY_PAYOUT_DETAILS).fetchInto(PartnerDutyPayoutDetailDao.class);
    }
}
