package com.goev.central.repository.promotion.impl;

import com.goev.central.dao.promotion.PromotionCustomerSegmentMappingDao;
import com.goev.central.repository.promotion.PromotionCustomerSegmentMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PromotionCustomerSegmentMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PromotionCustomerSegmentMappings.PROMOTION_CUSTOMER_SEGMENT_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class PromotionCustomerSegmentMappingRepositoryImpl implements PromotionCustomerSegmentMappingRepository {
    private final DSLContext context;

    @Override
    public PromotionCustomerSegmentMappingDao save(PromotionCustomerSegmentMappingDao log) {
        PromotionCustomerSegmentMappingsRecord promotionCustomerSegmentMappingsRecord = context.newRecord(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS, log);
        promotionCustomerSegmentMappingsRecord.store();
        log.setId(promotionCustomerSegmentMappingsRecord.getId());
        log.setUuid(promotionCustomerSegmentMappingsRecord.getUuid());
        log.setCreatedBy(promotionCustomerSegmentMappingsRecord.getCreatedBy());
        log.setUpdatedBy(promotionCustomerSegmentMappingsRecord.getUpdatedBy());
        log.setCreatedOn(promotionCustomerSegmentMappingsRecord.getCreatedOn());
        log.setUpdatedOn(promotionCustomerSegmentMappingsRecord.getUpdatedOn());
        log.setIsActive(promotionCustomerSegmentMappingsRecord.getIsActive());
        log.setState(promotionCustomerSegmentMappingsRecord.getState());
        log.setApiSource(promotionCustomerSegmentMappingsRecord.getApiSource());
        log.setNotes(promotionCustomerSegmentMappingsRecord.getNotes());
        return log;
    }

    @Override
    public PromotionCustomerSegmentMappingDao update(PromotionCustomerSegmentMappingDao log) {
        PromotionCustomerSegmentMappingsRecord promotionCustomerSegmentMappingsRecord = context.newRecord(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS, log);
        promotionCustomerSegmentMappingsRecord.update();


        log.setCreatedBy(promotionCustomerSegmentMappingsRecord.getCreatedBy());
        log.setUpdatedBy(promotionCustomerSegmentMappingsRecord.getUpdatedBy());
        log.setCreatedOn(promotionCustomerSegmentMappingsRecord.getCreatedOn());
        log.setUpdatedOn(promotionCustomerSegmentMappingsRecord.getUpdatedOn());
        log.setIsActive(promotionCustomerSegmentMappingsRecord.getIsActive());
        log.setState(promotionCustomerSegmentMappingsRecord.getState());
        log.setApiSource(promotionCustomerSegmentMappingsRecord.getApiSource());
        log.setNotes(promotionCustomerSegmentMappingsRecord.getNotes());
        return log;
    }

    @Override
    public void delete(Integer id) {
        context.update(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS)
                .set(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS.STATE, RecordState.DELETED.name())
                .where(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS.ID.eq(id))
                .and(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PromotionCustomerSegmentMappingDao findByUUID(String uuid) {
        return context.selectFrom(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS).where(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS.UUID.eq(uuid))
                .and(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PromotionCustomerSegmentMappingDao.class);
    }

    @Override
    public PromotionCustomerSegmentMappingDao findById(Integer id) {
        return context.selectFrom(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS).where(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS.ID.eq(id))
                .and(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PromotionCustomerSegmentMappingDao.class);
    }

    @Override
    public List<PromotionCustomerSegmentMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS).where(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS.ID.in(ids)).fetchInto(PromotionCustomerSegmentMappingDao.class);
    }

    @Override
    public List<PromotionCustomerSegmentMappingDao> findAllActive() {
        return context.selectFrom(PROMOTION_CUSTOMER_SEGMENT_MAPPINGS).fetchInto(PromotionCustomerSegmentMappingDao.class);
    }
}