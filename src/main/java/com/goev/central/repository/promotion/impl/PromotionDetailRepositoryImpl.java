package com.goev.central.repository.promotion.impl;

import com.goev.central.dao.promotion.PromotionDetailDao;
import com.goev.central.repository.promotion.PromotionDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PromotionDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PromotionDetails.PROMOTION_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class PromotionDetailRepositoryImpl implements PromotionDetailRepository {
    private final DSLContext context;

    @Override
    public PromotionDetailDao save(PromotionDetailDao log) {
        PromotionDetailsRecord promotionDetailsRecord = context.newRecord(PROMOTION_DETAILS, log);
        promotionDetailsRecord.store();
        log.setId(promotionDetailsRecord.getId());
        log.setUuid(promotionDetailsRecord.getUuid());
        log.setCreatedBy(promotionDetailsRecord.getCreatedBy());
        log.setUpdatedBy(promotionDetailsRecord.getUpdatedBy());
        log.setCreatedOn(promotionDetailsRecord.getCreatedOn());
        log.setUpdatedOn(promotionDetailsRecord.getUpdatedOn());
        log.setIsActive(promotionDetailsRecord.getIsActive());
        log.setState(promotionDetailsRecord.getState());
        log.setApiSource(promotionDetailsRecord.getApiSource());
        log.setNotes(promotionDetailsRecord.getNotes());
        return log;
    }

    @Override
    public PromotionDetailDao update(PromotionDetailDao log) {
        PromotionDetailsRecord promotionDetailsRecord = context.newRecord(PROMOTION_DETAILS, log);
        promotionDetailsRecord.update();


        log.setCreatedBy(promotionDetailsRecord.getCreatedBy());
        log.setUpdatedBy(promotionDetailsRecord.getUpdatedBy());
        log.setCreatedOn(promotionDetailsRecord.getCreatedOn());
        log.setUpdatedOn(promotionDetailsRecord.getUpdatedOn());
        log.setIsActive(promotionDetailsRecord.getIsActive());
        log.setState(promotionDetailsRecord.getState());
        log.setApiSource(promotionDetailsRecord.getApiSource());
        log.setNotes(promotionDetailsRecord.getNotes());
        return log;
    }

    @Override
    public void delete(Integer id) {
     context.update(PROMOTION_DETAILS)
     .set(PROMOTION_DETAILS.STATE,RecordState.DELETED.name())
     .where(PROMOTION_DETAILS.ID.eq(id))
     .and(PROMOTION_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
     .and(PROMOTION_DETAILS.IS_ACTIVE.eq(true))
     .execute();
    } 

     @Override
    public PromotionDetailDao findByUUID(String uuid) {
        return context.selectFrom(PROMOTION_DETAILS).where(PROMOTION_DETAILS.UUID.eq(uuid))
         .and(PROMOTION_DETAILS.IS_ACTIVE.eq(true))
        .fetchAnyInto(PromotionDetailDao.class);
    }  

    @Override
    public PromotionDetailDao findById(Integer id) {
        return context.selectFrom(PROMOTION_DETAILS).where(PROMOTION_DETAILS.ID.eq(id))
         .and(PROMOTION_DETAILS.IS_ACTIVE.eq(true))
        .fetchAnyInto(PromotionDetailDao.class);
    } 

    @Override
    public List<PromotionDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PROMOTION_DETAILS).where(PROMOTION_DETAILS.ID.in(ids)).fetchInto(PromotionDetailDao.class);
    }

    @Override
    public List<PromotionDetailDao> findAllActive() {
        return context.selectFrom(PROMOTION_DETAILS).fetchInto(PromotionDetailDao.class);
    }
}