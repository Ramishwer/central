package com.goev.central.repository.promotion.impl;

import com.goev.central.dao.promotion.PromotionBookingTypeMappingDao;
import com.goev.central.repository.promotion.PromotionBookingTypeMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PromotionBookingTypeMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PromotionBookingTypeMappings.PROMOTION_BOOKING_TYPE_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class PromotionBookingTypeMappingRepositoryImpl implements PromotionBookingTypeMappingRepository {
    private final DSLContext context;

    @Override
    public PromotionBookingTypeMappingDao save(PromotionBookingTypeMappingDao log) {
        PromotionBookingTypeMappingsRecord promotionBookingTypeMappingsRecord = context.newRecord(PROMOTION_BOOKING_TYPE_MAPPINGS, log);
        promotionBookingTypeMappingsRecord.store();
        log.setId(promotionBookingTypeMappingsRecord.getId());
        log.setUuid(promotionBookingTypeMappingsRecord.getUuid());
        log.setCreatedBy(promotionBookingTypeMappingsRecord.getCreatedBy());
        log.setUpdatedBy(promotionBookingTypeMappingsRecord.getUpdatedBy());
        log.setCreatedOn(promotionBookingTypeMappingsRecord.getCreatedOn());
        log.setUpdatedOn(promotionBookingTypeMappingsRecord.getUpdatedOn());
        log.setIsActive(promotionBookingTypeMappingsRecord.getIsActive());
        log.setState(promotionBookingTypeMappingsRecord.getState());
        log.setApiSource(promotionBookingTypeMappingsRecord.getApiSource());
        log.setNotes(promotionBookingTypeMappingsRecord.getNotes());
        return log;
    }

    @Override
    public PromotionBookingTypeMappingDao update(PromotionBookingTypeMappingDao log) {
        PromotionBookingTypeMappingsRecord promotionBookingTypeMappingsRecord = context.newRecord(PROMOTION_BOOKING_TYPE_MAPPINGS, log);
        promotionBookingTypeMappingsRecord.update();


        log.setCreatedBy(promotionBookingTypeMappingsRecord.getCreatedBy());
        log.setUpdatedBy(promotionBookingTypeMappingsRecord.getUpdatedBy());
        log.setCreatedOn(promotionBookingTypeMappingsRecord.getCreatedOn());
        log.setUpdatedOn(promotionBookingTypeMappingsRecord.getUpdatedOn());
        log.setIsActive(promotionBookingTypeMappingsRecord.getIsActive());
        log.setState(promotionBookingTypeMappingsRecord.getState());
        log.setApiSource(promotionBookingTypeMappingsRecord.getApiSource());
        log.setNotes(promotionBookingTypeMappingsRecord.getNotes());
        return log;
    }

    @Override
    public void delete(Integer id) {
     context.update(PROMOTION_BOOKING_TYPE_MAPPINGS)
     .set(PROMOTION_BOOKING_TYPE_MAPPINGS.STATE,RecordState.DELETED.name())
     .where(PROMOTION_BOOKING_TYPE_MAPPINGS.ID.eq(id))
     .and(PROMOTION_BOOKING_TYPE_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
     .and(PROMOTION_BOOKING_TYPE_MAPPINGS.IS_ACTIVE.eq(true))
     .execute();
    }

     @Override
    public PromotionBookingTypeMappingDao findByUUID(String uuid) {
        return context.selectFrom(PROMOTION_BOOKING_TYPE_MAPPINGS).where(PROMOTION_BOOKING_TYPE_MAPPINGS.UUID.eq(uuid))
         .and(PROMOTION_BOOKING_TYPE_MAPPINGS.IS_ACTIVE.eq(true))
        .fetchAnyInto(PromotionBookingTypeMappingDao.class);
    }

    @Override
    public PromotionBookingTypeMappingDao findById(Integer id) {
        return context.selectFrom(PROMOTION_BOOKING_TYPE_MAPPINGS).where(PROMOTION_BOOKING_TYPE_MAPPINGS.ID.eq(id))
         .and(PROMOTION_BOOKING_TYPE_MAPPINGS.IS_ACTIVE.eq(true))
        .fetchAnyInto(PromotionBookingTypeMappingDao.class);
    }

    @Override
    public List<PromotionBookingTypeMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PROMOTION_BOOKING_TYPE_MAPPINGS).where(PROMOTION_BOOKING_TYPE_MAPPINGS.ID.in(ids)).fetchInto(PromotionBookingTypeMappingDao.class);
    }

    @Override
    public List<PromotionBookingTypeMappingDao> findAllActive() {
        return context.selectFrom(PROMOTION_BOOKING_TYPE_MAPPINGS).fetchInto(PromotionBookingTypeMappingDao.class);
    }
}