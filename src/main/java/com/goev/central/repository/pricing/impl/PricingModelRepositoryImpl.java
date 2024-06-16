package com.goev.central.repository.pricing.impl;


import com.goev.central.dao.pricing.PricingModelDao;
import com.goev.central.repository.pricing.PricingModelRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PricingModelsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PricingModels.PRICING_MODELS;

@Slf4j
@Repository
@AllArgsConstructor
public class PricingModelRepositoryImpl implements PricingModelRepository {

    private final DSLContext context;

    @Override
    public PricingModelDao save(PricingModelDao pricingModelDao) {
        PricingModelsRecord pricingModelsRecord = context.newRecord(PRICING_MODELS, pricingModelDao);
        pricingModelsRecord.store();
        pricingModelDao.setId(pricingModelsRecord.getId());
        pricingModelDao.setUuid(pricingModelsRecord.getUuid());
        pricingModelDao.setCreatedBy(pricingModelsRecord.getCreatedBy());
        pricingModelDao.setUpdatedBy(pricingModelsRecord.getUpdatedBy());
        pricingModelDao.setCreatedOn(pricingModelsRecord.getCreatedOn());
        pricingModelDao.setUpdatedOn(pricingModelsRecord.getUpdatedOn());
        pricingModelDao.setIsActive(pricingModelsRecord.getIsActive());
        pricingModelDao.setState(pricingModelsRecord.getState());
        pricingModelDao.setApiSource(pricingModelsRecord.getApiSource());
        pricingModelDao.setNotes(pricingModelsRecord.getNotes());
        return pricingModelDao;
    }

    @Override
    public PricingModelDao update(PricingModelDao pricingModelDao) {
        PricingModelsRecord pricingModelsRecord = context.newRecord(PRICING_MODELS, pricingModelDao);
        pricingModelsRecord.update();


        pricingModelDao.setCreatedBy(pricingModelsRecord.getCreatedBy());
        pricingModelDao.setUpdatedBy(pricingModelsRecord.getUpdatedBy());
        pricingModelDao.setCreatedOn(pricingModelsRecord.getCreatedOn());
        pricingModelDao.setUpdatedOn(pricingModelsRecord.getUpdatedOn());
        pricingModelDao.setIsActive(pricingModelsRecord.getIsActive());
        pricingModelDao.setState(pricingModelsRecord.getState());
        pricingModelDao.setApiSource(pricingModelsRecord.getApiSource());
        pricingModelDao.setNotes(pricingModelsRecord.getNotes());
        return pricingModelDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(PRICING_MODELS)
                .set(PRICING_MODELS.STATE, RecordState.DELETED.name())
                .where(PRICING_MODELS.ID.eq(id))
                .and(PRICING_MODELS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PRICING_MODELS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PricingModelDao findByUUID(String uuid) {
        return context.selectFrom(PRICING_MODELS).where(PRICING_MODELS.UUID.eq(uuid))
                .and(PRICING_MODELS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PricingModelDao.class);
    }

    @Override
    public PricingModelDao findById(Integer id) {
        return context.selectFrom(PRICING_MODELS).where(PRICING_MODELS.ID.eq(id))
                .and(PRICING_MODELS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PricingModelDao.class);
    }

    @Override
    public List<PricingModelDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PRICING_MODELS).where(PRICING_MODELS.ID.in(ids)).fetchInto(PricingModelDao.class);
    }

    @Override
    public List<PricingModelDao> findAllActive() {
        return context.selectFrom(PRICING_MODELS).fetchInto(PricingModelDao.class);
    }
}
