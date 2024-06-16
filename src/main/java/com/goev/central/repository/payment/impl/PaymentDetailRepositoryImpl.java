package com.goev.central.repository.payment.impl;

import com.goev.central.dao.payment.PaymentDetailDao;
import com.goev.central.repository.payment.PaymentDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PaymentDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PaymentDetails.PAYMENT_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class PaymentDetailRepositoryImpl implements PaymentDetailRepository {
    private final DSLContext context;

    @Override
    public PaymentDetailDao save(PaymentDetailDao log) {
        PaymentDetailsRecord paymentDetailsRecord = context.newRecord(PAYMENT_DETAILS, log);
        paymentDetailsRecord.store();
        log.setId(paymentDetailsRecord.getId());
        log.setUuid(paymentDetailsRecord.getUuid());
        log.setCreatedBy(paymentDetailsRecord.getCreatedBy());
        log.setUpdatedBy(paymentDetailsRecord.getUpdatedBy());
        log.setCreatedOn(paymentDetailsRecord.getCreatedOn());
        log.setUpdatedOn(paymentDetailsRecord.getUpdatedOn());
        log.setIsActive(paymentDetailsRecord.getIsActive());
        log.setState(paymentDetailsRecord.getState());
        log.setApiSource(paymentDetailsRecord.getApiSource());
        log.setNotes(paymentDetailsRecord.getNotes());
        return log;
    }

    @Override
    public PaymentDetailDao update(PaymentDetailDao log) {
        PaymentDetailsRecord paymentDetailsRecord = context.newRecord(PAYMENT_DETAILS, log);
        paymentDetailsRecord.update();


        log.setCreatedBy(paymentDetailsRecord.getCreatedBy());
        log.setUpdatedBy(paymentDetailsRecord.getUpdatedBy());
        log.setCreatedOn(paymentDetailsRecord.getCreatedOn());
        log.setUpdatedOn(paymentDetailsRecord.getUpdatedOn());
        log.setIsActive(paymentDetailsRecord.getIsActive());
        log.setState(paymentDetailsRecord.getState());
        log.setApiSource(paymentDetailsRecord.getApiSource());
        log.setNotes(paymentDetailsRecord.getNotes());
        return log;
    }

    @Override
    public void delete(Integer id) {
        context.update(PAYMENT_DETAILS)
                .set(PAYMENT_DETAILS.STATE, RecordState.DELETED.name())
                .where(PAYMENT_DETAILS.ID.eq(id))
                .and(PAYMENT_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PAYMENT_DETAILS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PaymentDetailDao findByUUID(String uuid) {
        return context.selectFrom(PAYMENT_DETAILS).where(PAYMENT_DETAILS.UUID.eq(uuid))
                .and(PAYMENT_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PaymentDetailDao.class);
    }

    @Override
    public PaymentDetailDao findById(Integer id) {
        return context.selectFrom(PAYMENT_DETAILS).where(PAYMENT_DETAILS.ID.eq(id))
                .and(PAYMENT_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PaymentDetailDao.class);
    }

    @Override
    public List<PaymentDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PAYMENT_DETAILS).where(PAYMENT_DETAILS.ID.in(ids)).fetchInto(PaymentDetailDao.class);
    }

    @Override
    public List<PaymentDetailDao> findAllActive() {
        return context.selectFrom(PAYMENT_DETAILS).fetchInto(PaymentDetailDao.class);
    }
}