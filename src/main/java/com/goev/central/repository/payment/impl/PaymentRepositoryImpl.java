package com.goev.central.repository.payment.impl;

import com.goev.central.dao.payment.PaymentDao;
import com.goev.central.repository.payment.PaymentRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PaymentsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Payments.PAYMENTS;

@Slf4j
@Repository
@AllArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final DSLContext context;

    @Override
    public PaymentDao save(PaymentDao log) {
        PaymentsRecord paymentsRecord = context.newRecord(PAYMENTS, log);
        paymentsRecord.store();
        log.setId(paymentsRecord.getId());
        log.setUuid(paymentsRecord.getUuid());
        log.setCreatedBy(paymentsRecord.getCreatedBy());
        log.setUpdatedBy(paymentsRecord.getUpdatedBy());
        log.setCreatedOn(paymentsRecord.getCreatedOn());
        log.setUpdatedOn(paymentsRecord.getUpdatedOn());
        log.setIsActive(paymentsRecord.getIsActive());
        log.setState(paymentsRecord.getState());
        log.setApiSource(paymentsRecord.getApiSource());
        log.setNotes(paymentsRecord.getNotes());
        return log;
    }

    @Override
    public PaymentDao update(PaymentDao log) {
        PaymentsRecord paymentsRecord = context.newRecord(PAYMENTS, log);
        paymentsRecord.update();


        log.setCreatedBy(paymentsRecord.getCreatedBy());
        log.setUpdatedBy(paymentsRecord.getUpdatedBy());
        log.setCreatedOn(paymentsRecord.getCreatedOn());
        log.setUpdatedOn(paymentsRecord.getUpdatedOn());
        log.setIsActive(paymentsRecord.getIsActive());
        log.setState(paymentsRecord.getState());
        log.setApiSource(paymentsRecord.getApiSource());
        log.setNotes(paymentsRecord.getNotes());
        return log;
    }

    @Override
    public void delete(Integer id) {
        context.update(PAYMENTS)
                .set(PAYMENTS.STATE, RecordState.DELETED.name())
                .where(PAYMENTS.ID.eq(id))
                .and(PAYMENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PAYMENTS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PaymentDao findByUUID(String uuid) {
        return context.selectFrom(PAYMENTS).where(PAYMENTS.UUID.eq(uuid))
                .and(PAYMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PaymentDao.class);
    }

    @Override
    public PaymentDao findById(Integer id) {
        return context.selectFrom(PAYMENTS).where(PAYMENTS.ID.eq(id))
                .and(PAYMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PaymentDao.class);
    }

    @Override
    public List<PaymentDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PAYMENTS).where(PAYMENTS.ID.in(ids)).fetchInto(PaymentDao.class);
    }

    @Override
    public List<PaymentDao> findAllActive() {
        return context.selectFrom(PAYMENTS).fetchInto(PaymentDao.class);
    }
}