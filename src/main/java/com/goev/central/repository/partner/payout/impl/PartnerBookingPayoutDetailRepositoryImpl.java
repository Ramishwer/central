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
        partnerBookingPayoutDetail.setCreatedBy(partnerBookingPayoutDetail.getCreatedBy());
        partnerBookingPayoutDetail.setUpdatedBy(partnerBookingPayoutDetail.getUpdatedBy());
        partnerBookingPayoutDetail.setCreatedOn(partnerBookingPayoutDetail.getCreatedOn());
        partnerBookingPayoutDetail.setUpdatedOn(partnerBookingPayoutDetail.getUpdatedOn());
        partnerBookingPayoutDetail.setIsActive(partnerBookingPayoutDetail.getIsActive());
        partnerBookingPayoutDetail.setState(partnerBookingPayoutDetail.getState());
        partnerBookingPayoutDetail.setApiSource(partnerBookingPayoutDetail.getApiSource());
        partnerBookingPayoutDetail.setNotes(partnerBookingPayoutDetail.getNotes());
        return partnerBookingPayoutDetail;
    }

    @Override
    public PartnerBookingPayoutDetailDao update(PartnerBookingPayoutDetailDao partnerBookingPayoutDetail) {
        PartnerBookingPayoutDetailsRecord partnerBookingPayoutDetailsRecord = context.newRecord(PARTNER_BOOKING_PAYOUT_DETAILS, partnerBookingPayoutDetail);
        partnerBookingPayoutDetailsRecord.update();


        partnerBookingPayoutDetail.setCreatedBy(partnerBookingPayoutDetailsRecord.getCreatedBy());
        partnerBookingPayoutDetail.setUpdatedBy(partnerBookingPayoutDetailsRecord.getUpdatedBy());
        partnerBookingPayoutDetail.setCreatedOn(partnerBookingPayoutDetailsRecord.getCreatedOn());
        partnerBookingPayoutDetail.setUpdatedOn(partnerBookingPayoutDetailsRecord.getUpdatedOn());
        partnerBookingPayoutDetail.setIsActive(partnerBookingPayoutDetailsRecord.getIsActive());
        partnerBookingPayoutDetail.setState(partnerBookingPayoutDetailsRecord.getState());
        partnerBookingPayoutDetail.setApiSource(partnerBookingPayoutDetailsRecord.getApiSource());
        partnerBookingPayoutDetail.setNotes(partnerBookingPayoutDetailsRecord.getNotes());
        return partnerBookingPayoutDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_BOOKING_PAYOUT_DETAILS)
                .set(PARTNER_BOOKING_PAYOUT_DETAILS.STATE, RecordState.DELETED.name())
                .where(PARTNER_BOOKING_PAYOUT_DETAILS.ID.eq(id))
                .and(PARTNER_BOOKING_PAYOUT_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_BOOKING_PAYOUT_DETAILS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerBookingPayoutDetailDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_BOOKING_PAYOUT_DETAILS).where(PARTNER_BOOKING_PAYOUT_DETAILS.UUID.eq(uuid))
                .and(PARTNER_BOOKING_PAYOUT_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerBookingPayoutDetailDao.class);
    }

    @Override
    public PartnerBookingPayoutDetailDao findById(Integer id) {
        return context.selectFrom(PARTNER_BOOKING_PAYOUT_DETAILS).where(PARTNER_BOOKING_PAYOUT_DETAILS.ID.eq(id))
                .and(PARTNER_BOOKING_PAYOUT_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerBookingPayoutDetailDao.class);
    }

    @Override
    public List<PartnerBookingPayoutDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_BOOKING_PAYOUT_DETAILS).where(PARTNER_BOOKING_PAYOUT_DETAILS.ID.in(ids)).fetchInto(PartnerBookingPayoutDetailDao.class);
    }

    @Override
    public List<PartnerBookingPayoutDetailDao> findAllActive() {
        return context.selectFrom(PARTNER_BOOKING_PAYOUT_DETAILS).fetchInto(PartnerBookingPayoutDetailDao.class);
    }
}
