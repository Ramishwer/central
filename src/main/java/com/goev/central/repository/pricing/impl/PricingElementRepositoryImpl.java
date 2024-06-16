package com.goev.central.repository.pricing.impl;


import com.goev.central.dao.pricing.PricingElementDao;
import com.goev.central.repository.pricing.PricingElementRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PricingElementsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PricingElements.PRICING_ELEMENTS;

@Slf4j
@Repository
@AllArgsConstructor
public class PricingElementRepositoryImpl implements PricingElementRepository {

    private final DSLContext context;

    @Override
    public PricingElementDao save(PricingElementDao pricingElementDao) {
        PricingElementsRecord pricingElementsRecord = context.newRecord(PRICING_ELEMENTS, pricingElementDao);
        pricingElementsRecord.store();
        pricingElementDao.setId(pricingElementsRecord.getId());
        pricingElementDao.setUuid(pricingElementsRecord.getUuid());
        pricingElementDao.setCreatedBy(pricingElementsRecord.getCreatedBy());
        pricingElementDao.setUpdatedBy(pricingElementsRecord.getUpdatedBy());
        pricingElementDao.setCreatedOn(pricingElementsRecord.getCreatedOn());
        pricingElementDao.setUpdatedOn(pricingElementsRecord.getUpdatedOn());
        pricingElementDao.setIsActive(pricingElementsRecord.getIsActive());
        pricingElementDao.setState(pricingElementsRecord.getState());
        pricingElementDao.setApiSource(pricingElementsRecord.getApiSource());
        pricingElementDao.setNotes(pricingElementsRecord.getNotes());
        return pricingElementDao;
    }

    @Override
    public PricingElementDao update(PricingElementDao pricingElementDao) {
        PricingElementsRecord pricingElementsRecord = context.newRecord(PRICING_ELEMENTS, pricingElementDao);
        pricingElementsRecord.update();


        pricingElementDao.setCreatedBy(pricingElementsRecord.getCreatedBy());
        pricingElementDao.setUpdatedBy(pricingElementsRecord.getUpdatedBy());
        pricingElementDao.setCreatedOn(pricingElementsRecord.getCreatedOn());
        pricingElementDao.setUpdatedOn(pricingElementsRecord.getUpdatedOn());
        pricingElementDao.setIsActive(pricingElementsRecord.getIsActive());
        pricingElementDao.setState(pricingElementsRecord.getState());
        pricingElementDao.setApiSource(pricingElementsRecord.getApiSource());
        pricingElementDao.setNotes(pricingElementsRecord.getNotes());
        return pricingElementDao;
    }

    @Override
    public void delete(Integer id) {
     context.update(PRICING_ELEMENTS)
     .set(PRICING_ELEMENTS.STATE,RecordState.DELETED.name())
     .where(PRICING_ELEMENTS.ID.eq(id))
     .and(PRICING_ELEMENTS.STATE.eq(RecordState.ACTIVE.name()))
     .and(PRICING_ELEMENTS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public PricingElementDao findByUUID(String uuid) {
        return context.selectFrom(PRICING_ELEMENTS).where(PRICING_ELEMENTS.UUID.eq(uuid))
                .and(PRICING_ELEMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PricingElementDao.class);
    }

    @Override
    public PricingElementDao findById(Integer id) {
        return context.selectFrom(PRICING_ELEMENTS).where(PRICING_ELEMENTS.ID.eq(id))
                .and(PRICING_ELEMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PricingElementDao.class);
    }

    @Override
    public List<PricingElementDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PRICING_ELEMENTS).where(PRICING_ELEMENTS.ID.in(ids)).fetchInto(PricingElementDao.class);
    }

    @Override
    public List<PricingElementDao> findAllActive() {
        return context.selectFrom(PRICING_ELEMENTS).fetchInto(PricingElementDao.class);
    }
}
