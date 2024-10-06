package com.goev.central.repository.payout.impl;

import com.goev.central.dao.payout.PayoutElementDao;
import com.goev.central.repository.payout.PayoutElementRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PayoutElementsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PayoutElements.PAYOUT_ELEMENTS;

@Slf4j
@Repository
@AllArgsConstructor
public class PayoutElementRepositoryImpl implements PayoutElementRepository {

    private final DSLContext context;

    @Override
    public PayoutElementDao save(PayoutElementDao payoutElement) {
        PayoutElementsRecord payoutElementsRecord = context.newRecord(PAYOUT_ELEMENTS, payoutElement);
        payoutElementsRecord.store();
        payoutElement.setId(payoutElementsRecord.getId());
        payoutElement.setUuid(payoutElementsRecord.getUuid());
        payoutElement.setCreatedBy(payoutElementsRecord.getCreatedBy());
        payoutElement.setUpdatedBy(payoutElementsRecord.getUpdatedBy());
        payoutElement.setCreatedOn(payoutElementsRecord.getCreatedOn());
        payoutElement.setUpdatedOn(payoutElementsRecord.getUpdatedOn());
        payoutElement.setIsActive(payoutElementsRecord.getIsActive());
        payoutElement.setState(payoutElementsRecord.getState());
        payoutElement.setApiSource(payoutElementsRecord.getApiSource());
        payoutElement.setNotes(payoutElementsRecord.getNotes());
        return payoutElement;
    }

    @Override
    public PayoutElementDao update(PayoutElementDao payoutElement) {
        PayoutElementsRecord payoutElementsRecord = context.newRecord(PAYOUT_ELEMENTS, payoutElement);
        payoutElementsRecord.update();


        payoutElement.setCreatedBy(payoutElementsRecord.getCreatedBy());
        payoutElement.setUpdatedBy(payoutElementsRecord.getUpdatedBy());
        payoutElement.setCreatedOn(payoutElementsRecord.getCreatedOn());
        payoutElement.setUpdatedOn(payoutElementsRecord.getUpdatedOn());
        payoutElement.setIsActive(payoutElementsRecord.getIsActive());
        payoutElement.setState(payoutElementsRecord.getState());
        payoutElement.setApiSource(payoutElementsRecord.getApiSource());
        payoutElement.setNotes(payoutElementsRecord.getNotes());
        return payoutElement;
    }

    @Override
    public void delete(Integer id) {
        context.update(PAYOUT_ELEMENTS)
                .set(PAYOUT_ELEMENTS.STATE, RecordState.DELETED.name())
                .where(PAYOUT_ELEMENTS.ID.eq(id))
                .and(PAYOUT_ELEMENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PAYOUT_ELEMENTS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PayoutElementDao findByUUID(String uuid) {
        return context.selectFrom(PAYOUT_ELEMENTS).where(PAYOUT_ELEMENTS.UUID.eq(uuid))
                .and(PAYOUT_ELEMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PayoutElementDao.class);
    }

    @Override
    public PayoutElementDao findById(Integer id) {
        return context.selectFrom(PAYOUT_ELEMENTS).where(PAYOUT_ELEMENTS.ID.eq(id))
                .and(PAYOUT_ELEMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PayoutElementDao.class);
    }

    @Override
    public List<PayoutElementDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PAYOUT_ELEMENTS)
                .where(PAYOUT_ELEMENTS.ID.in(ids))
                .orderBy(PAYOUT_ELEMENTS.SORTING_ORDER)
                .fetchInto(PayoutElementDao.class);
    }

    @Override
    public List<PayoutElementDao> findAllActive() {
        return context.selectFrom(PAYOUT_ELEMENTS).fetchInto(PayoutElementDao.class);
    }
}
