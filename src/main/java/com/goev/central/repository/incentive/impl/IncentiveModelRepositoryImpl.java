package com.goev.central.repository.incentive.impl;

import com.goev.central.dao.incentive.IncentiveModelDao;
import com.goev.central.repository.incentive.IncentiveModelRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.IncentiveModelsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.IncentiveModels.INCENTIVE_MODELS;

@Slf4j
@Repository
@AllArgsConstructor
public class IncentiveModelRepositoryImpl implements IncentiveModelRepository {

    private final DSLContext context;

    @Override
    public IncentiveModelDao save(IncentiveModelDao incentiveModel) {
        IncentiveModelsRecord incentiveModelsRecord = context.newRecord(INCENTIVE_MODELS, incentiveModel);
        incentiveModelsRecord.store();
        incentiveModel.setId(incentiveModelsRecord.getId());
        incentiveModel.setUuid(incentiveModelsRecord.getUuid());
        incentiveModel.setCreatedBy(incentiveModelsRecord.getCreatedBy());
        incentiveModel.setUpdatedBy(incentiveModelsRecord.getUpdatedBy());
        incentiveModel.setCreatedOn(incentiveModelsRecord.getCreatedOn());
        incentiveModel.setUpdatedOn(incentiveModelsRecord.getUpdatedOn());
        incentiveModel.setIsActive(incentiveModelsRecord.getIsActive());
        incentiveModel.setState(incentiveModelsRecord.getState());
        incentiveModel.setApiSource(incentiveModelsRecord.getApiSource());
        incentiveModel.setNotes(incentiveModelsRecord.getNotes());
        return incentiveModel;
    }

    @Override
    public IncentiveModelDao update(IncentiveModelDao incentiveModel) {
        IncentiveModelsRecord incentiveModelsRecord = context.newRecord(INCENTIVE_MODELS, incentiveModel);
        incentiveModelsRecord.update();


        incentiveModel.setCreatedBy(incentiveModelsRecord.getCreatedBy());
        incentiveModel.setUpdatedBy(incentiveModelsRecord.getUpdatedBy());
        incentiveModel.setCreatedOn(incentiveModelsRecord.getCreatedOn());
        incentiveModel.setUpdatedOn(incentiveModelsRecord.getUpdatedOn());
        incentiveModel.setIsActive(incentiveModelsRecord.getIsActive());
        incentiveModel.setState(incentiveModelsRecord.getState());
        incentiveModel.setApiSource(incentiveModelsRecord.getApiSource());
        incentiveModel.setNotes(incentiveModelsRecord.getNotes());
        return incentiveModel;
    }

    @Override
    public void delete(Integer id) {
        context.update(INCENTIVE_MODELS)
                .set(INCENTIVE_MODELS.STATE, RecordState.DELETED.name())
                .where(INCENTIVE_MODELS.ID.eq(id))
                .and(INCENTIVE_MODELS.STATE.eq(RecordState.ACTIVE.name()))
                .and(INCENTIVE_MODELS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public IncentiveModelDao findByUUID(String uuid) {
        return context.selectFrom(INCENTIVE_MODELS).where(INCENTIVE_MODELS.UUID.eq(uuid))
                .and(INCENTIVE_MODELS.IS_ACTIVE.eq(true))
                .fetchAnyInto(IncentiveModelDao.class);
    }

    @Override
    public IncentiveModelDao findById(Integer id) {
        return context.selectFrom(INCENTIVE_MODELS).where(INCENTIVE_MODELS.ID.eq(id))
                .and(INCENTIVE_MODELS.IS_ACTIVE.eq(true))
                .fetchAnyInto(IncentiveModelDao.class);
    }

    @Override
    public List<IncentiveModelDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(INCENTIVE_MODELS).where(INCENTIVE_MODELS.ID.in(ids)).fetchInto(IncentiveModelDao.class);
    }

    @Override
    public List<IncentiveModelDao> findAllActive() {
        return context.selectFrom(INCENTIVE_MODELS).fetchInto(IncentiveModelDao.class);
    }
}
