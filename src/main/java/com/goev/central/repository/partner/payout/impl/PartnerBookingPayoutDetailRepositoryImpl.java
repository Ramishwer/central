package com.goev.central.repository.partner.payout.impl;

import com.goev.central.dao.partner.payout.PartnerBookingPayoutDetailDao;
import com.goev.central.repository.partner.payout.PartnerBookingPayoutDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerBookingPayoutDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerBookingPayoutDetails.PARTNER_BOOKING_PAYOUT_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerBookingPayoutDetailRepositoryImpl implements PartnerBookingPayoutDetailRepository {

    private final DSLContext context;

    @Override
    public PartnerBookingPayoutDetailDao save(PartnerBookingPayoutDetailDao partnerBookingPayoutDetail) {
        PartnerBookingPayoutDetailsRecord partnerBookingPayoutDetailsRecord = context.newRecord(PARTNER_BOOKING_PAYOUT_DETAILS, partnerBookingPayoutDetail);
        partnerBookingPayoutDetailsRecord.store();
        partnerBookingPayoutDetail.setId(partnerBookingPayoutDetailsRecord.getId());
        partnerBookingPayoutDetail.setUuid(partnerBookingPayoutDetail.getUuid());
        return partnerBookingPayoutDetail;
    }

    @Override
    public PartnerBookingPayoutDetailDao update(PartnerBookingPayoutDetailDao partnerBookingPayoutDetail) {
        PartnerBookingPayoutDetailsRecord partnerBookingPayoutDetailsRecord = context.newRecord(PARTNER_BOOKING_PAYOUT_DETAILS, partnerBookingPayoutDetail);
        partnerBookingPayoutDetailsRecord.update();
        return partnerBookingPayoutDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_BOOKING_PAYOUT_DETAILS).set(PARTNER_BOOKING_PAYOUT_DETAILS.STATE, RecordState.DELETED.name()).where(PARTNER_BOOKING_PAYOUT_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public PartnerBookingPayoutDetailDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_BOOKING_PAYOUT_DETAILS).where(PARTNER_BOOKING_PAYOUT_DETAILS.UUID.eq(uuid)).fetchAnyInto(PartnerBookingPayoutDetailDao.class);
    }

    @Override
    public PartnerBookingPayoutDetailDao findById(Integer id) {
        return context.selectFrom(PARTNER_BOOKING_PAYOUT_DETAILS).where(PARTNER_BOOKING_PAYOUT_DETAILS.ID.eq(id)).fetchAnyInto(PartnerBookingPayoutDetailDao.class);
    }

    @Override
    public List<PartnerBookingPayoutDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_BOOKING_PAYOUT_DETAILS).where(PARTNER_BOOKING_PAYOUT_DETAILS.ID.in(ids)).fetchInto(PartnerBookingPayoutDetailDao.class);
    }

    @Override
    public List<PartnerBookingPayoutDetailDao> findAll() {
        return context.selectFrom(PARTNER_BOOKING_PAYOUT_DETAILS).fetchInto(PartnerBookingPayoutDetailDao.class);
    }
}
