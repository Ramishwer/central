package com.goev.central.repository.promotion.impl;

import com.goev.central.dao.promotion.PromotionRegionMappingDao;
import com.goev.central.repository.promotion.PromotionRegionMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PromotionRegionMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PromotionRegionMappings.PROMOTION_REGION_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class PromotionRegionMappingRepositoryImpl implements PromotionRegionMappingRepository {
    private final DSLContext context;

    @Override
    public PromotionRegionMappingDao save(PromotionRegionMappingDao log) {
        PromotionRegionMappingsRecord promotionRegionMappingsRecord = context.newRecord(PROMOTION_REGION_MAPPINGS, log);
        promotionRegionMappingsRecord.store();
        log.setId(promotionRegionMappingsRecord.getId());
        log.setUuid(promotionRegionMappingsRecord.getUuid());
        log.setCreatedBy(promotionRegionMappingsRecord.getCreatedBy());
        log.setUpdatedBy(promotionRegionMappingsRecord.getUpdatedBy());
        log.setCreatedOn(promotionRegionMappingsRecord.getCreatedOn());
        log.setUpdatedOn(promotionRegionMappingsRecord.getUpdatedOn());
        log.setIsActive(promotionRegionMappingsRecord.getIsActive());
        log.setState(promotionRegionMappingsRecord.getState());
        log.setApiSource(promotionRegionMappingsRecord.getApiSource());
        log.setNotes(promotionRegionMappingsRecord.getNotes());
        return log;
    }

    @Override
    public PromotionRegionMappingDao update(PromotionRegionMappingDao log) {
        PromotionRegionMappingsRecord promotionRegionMappingsRecord = context.newRecord(PROMOTION_REGION_MAPPINGS, log);
        promotionRegionMappingsRecord.update();


        log.setCreatedBy(promotionRegionMappingsRecord.getCreatedBy());
        log.setUpdatedBy(promotionRegionMappingsRecord.getUpdatedBy());
        log.setCreatedOn(promotionRegionMappingsRecord.getCreatedOn());
        log.setUpdatedOn(promotionRegionMappingsRecord.getUpdatedOn());
        log.setIsActive(promotionRegionMappingsRecord.getIsActive());
        log.setState(promotionRegionMappingsRecord.getState());
        log.setApiSource(promotionRegionMappingsRecord.getApiSource());
        log.setNotes(promotionRegionMappingsRecord.getNotes());
        return log;
    }

    @Override
    public void delete(Integer id) {
     context.update(PROMOTION_REGION_MAPPINGS)
     .set(PROMOTION_REGION_MAPPINGS.STATE,RecordState.DELETED.name())
     .where(PROMOTION_REGION_MAPPINGS.ID.eq(id))
     .and(PROMOTION_REGION_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
     .and(PROMOTION_REGION_MAPPINGS.IS_ACTIVE.eq(true))
     .execute();
    } 

     @Override
    public PromotionRegionMappingDao findByUUID(String uuid) {
        return context.selectFrom(PROMOTION_REGION_MAPPINGS).where(PROMOTION_REGION_MAPPINGS.UUID.eq(uuid))
         .and(PROMOTION_REGION_MAPPINGS.IS_ACTIVE.eq(true))
        .fetchAnyInto(PromotionRegionMappingDao.class);
    }  

    @Override
    public PromotionRegionMappingDao findById(Integer id) {
        return context.selectFrom(PROMOTION_REGION_MAPPINGS).where(PROMOTION_REGION_MAPPINGS.ID.eq(id))
         .and(PROMOTION_REGION_MAPPINGS.IS_ACTIVE.eq(true))
        .fetchAnyInto(PromotionRegionMappingDao.class);
    } 

    @Override
    public List<PromotionRegionMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PROMOTION_REGION_MAPPINGS).where(PROMOTION_REGION_MAPPINGS.ID.in(ids)).fetchInto(PromotionRegionMappingDao.class);
    }

    @Override
    public List<PromotionRegionMappingDao> findAllActive() {
        return context.selectFrom(PROMOTION_REGION_MAPPINGS).fetchInto(PromotionRegionMappingDao.class);
    }
}