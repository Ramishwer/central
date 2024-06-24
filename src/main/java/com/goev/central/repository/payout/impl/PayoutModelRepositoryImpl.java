package com.goev.central.repository.payout.impl;

import com.goev.central.dao.payout.PayoutModelDao;
import com.goev.central.repository.payout.PayoutModelRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PayoutModelsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PayoutModels.PAYOUT_MODELS;

@Slf4j
@Repository
@AllArgsConstructor
public class PayoutModelRepositoryImpl implements PayoutModelRepository {

    private final DSLContext context;

    @Override
    public PayoutModelDao save(PayoutModelDao payoutModel) {
        PayoutModelsRecord payoutModelsRecord = context.newRecord(PAYOUT_MODELS, payoutModel);
        payoutModelsRecord.store();
        payoutModel.setId(payoutModelsRecord.getId());
        payoutModel.setUuid(payoutModelsRecord.getUuid());
        payoutModel.setCreatedBy(payoutModelsRecord.getCreatedBy());
        payoutModel.setUpdatedBy(payoutModelsRecord.getUpdatedBy());
        payoutModel.setCreatedOn(payoutModelsRecord.getCreatedOn());
        payoutModel.setUpdatedOn(payoutModelsRecord.getUpdatedOn());
        payoutModel.setIsActive(payoutModelsRecord.getIsActive());
        payoutModel.setState(payoutModelsRecord.getState());
        payoutModel.setApiSource(payoutModelsRecord.getApiSource());
        payoutModel.setNotes(payoutModelsRecord.getNotes());
        return payoutModel;
    }

    @Override
    public PayoutModelDao update(PayoutModelDao payoutModel) {
        PayoutModelsRecord payoutModelsRecord = context.newRecord(PAYOUT_MODELS, payoutModel);
        payoutModelsRecord.update();


        payoutModel.setCreatedBy(payoutModelsRecord.getCreatedBy());
        payoutModel.setUpdatedBy(payoutModelsRecord.getUpdatedBy());
        payoutModel.setCreatedOn(payoutModelsRecord.getCreatedOn());
        payoutModel.setUpdatedOn(payoutModelsRecord.getUpdatedOn());
        payoutModel.setIsActive(payoutModelsRecord.getIsActive());
        payoutModel.setState(payoutModelsRecord.getState());
        payoutModel.setApiSource(payoutModelsRecord.getApiSource());
        payoutModel.setNotes(payoutModelsRecord.getNotes());
        return payoutModel;
    }

    @Override
    public void delete(Integer id) {
        context.update(PAYOUT_MODELS)
                .set(PAYOUT_MODELS.STATE, RecordState.DELETED.name())
                .where(PAYOUT_MODELS.ID.eq(id))
                .and(PAYOUT_MODELS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PAYOUT_MODELS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PayoutModelDao findByUUID(String uuid) {
        return context.selectFrom(PAYOUT_MODELS).where(PAYOUT_MODELS.UUID.eq(uuid))
                .and(PAYOUT_MODELS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PayoutModelDao.class);
    }

    @Override
    public PayoutModelDao findById(Integer id) {
        return context.selectFrom(PAYOUT_MODELS).where(PAYOUT_MODELS.ID.eq(id))
                .and(PAYOUT_MODELS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PayoutModelDao.class);
    }

    @Override
    public List<PayoutModelDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PAYOUT_MODELS).where(PAYOUT_MODELS.ID.in(ids)).fetchInto(PayoutModelDao.class);
    }

    @Override
    public List<PayoutModelDao> findAllActive() {
        return context.selectFrom(PAYOUT_MODELS).fetchInto(PayoutModelDao.class);
    }
}
