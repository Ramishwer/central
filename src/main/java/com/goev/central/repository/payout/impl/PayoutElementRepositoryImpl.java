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
        payoutElement.setUuid(payoutElement.getUuid());
        return payoutElement;
    }

    @Override
    public PayoutElementDao update(PayoutElementDao payoutElement) {
        PayoutElementsRecord payoutElementsRecord = context.newRecord(PAYOUT_ELEMENTS, payoutElement);
        payoutElementsRecord.update();
        return payoutElement;
    }

    @Override
    public void delete(Integer id) {
        context.update(PAYOUT_ELEMENTS).set(PAYOUT_ELEMENTS.STATE, RecordState.DELETED.name()).where(PAYOUT_ELEMENTS.ID.eq(id)).execute();
    }

    @Override
    public PayoutElementDao findByUUID(String uuid) {
        return context.selectFrom(PAYOUT_ELEMENTS).where(PAYOUT_ELEMENTS.UUID.eq(uuid)).fetchAnyInto(PayoutElementDao.class);
    }

    @Override
    public PayoutElementDao findById(Integer id) {
        return context.selectFrom(PAYOUT_ELEMENTS).where(PAYOUT_ELEMENTS.ID.eq(id)).fetchAnyInto(PayoutElementDao.class);
    }

    @Override
    public List<PayoutElementDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PAYOUT_ELEMENTS).where(PAYOUT_ELEMENTS.ID.in(ids)).fetchInto(PayoutElementDao.class);
    }

    @Override
    public List<PayoutElementDao> findAll() {
        return context.selectFrom(PAYOUT_ELEMENTS).fetchInto(PayoutElementDao.class);
    }
}
