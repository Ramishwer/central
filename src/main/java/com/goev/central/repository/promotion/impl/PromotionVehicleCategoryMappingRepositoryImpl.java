package com.goev.central.repository.promotion.impl;

import com.goev.central.dao.promotion.PromotionVehicleCategoryMappingDao;
import com.goev.central.repository.promotion.PromotionVehicleCategoryMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PromotionVehicleCategoryMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PromotionVehicleCategoryMappings.PROMOTION_VEHICLE_CATEGORY_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class PromotionVehicleCategoryMappingRepositoryImpl implements PromotionVehicleCategoryMappingRepository {
    private final DSLContext context;

    @Override
    public PromotionVehicleCategoryMappingDao save(PromotionVehicleCategoryMappingDao log) {
        PromotionVehicleCategoryMappingsRecord promotionVehicleCategoryMappingsRecord = context.newRecord(PROMOTION_VEHICLE_CATEGORY_MAPPINGS, log);
        promotionVehicleCategoryMappingsRecord.store();
        log.setId(promotionVehicleCategoryMappingsRecord.getId());
        log.setUuid(promotionVehicleCategoryMappingsRecord.getUuid());
        log.setCreatedBy(promotionVehicleCategoryMappingsRecord.getCreatedBy());
        log.setUpdatedBy(promotionVehicleCategoryMappingsRecord.getUpdatedBy());
        log.setCreatedOn(promotionVehicleCategoryMappingsRecord.getCreatedOn());
        log.setUpdatedOn(promotionVehicleCategoryMappingsRecord.getUpdatedOn());
        log.setIsActive(promotionVehicleCategoryMappingsRecord.getIsActive());
        log.setState(promotionVehicleCategoryMappingsRecord.getState());
        log.setApiSource(promotionVehicleCategoryMappingsRecord.getApiSource());
        log.setNotes(promotionVehicleCategoryMappingsRecord.getNotes());
        return log;
    }

    @Override
    public PromotionVehicleCategoryMappingDao update(PromotionVehicleCategoryMappingDao log) {
        PromotionVehicleCategoryMappingsRecord promotionVehicleCategoryMappingsRecord = context.newRecord(PROMOTION_VEHICLE_CATEGORY_MAPPINGS, log);
        promotionVehicleCategoryMappingsRecord.update();


        log.setCreatedBy(promotionVehicleCategoryMappingsRecord.getCreatedBy());
        log.setUpdatedBy(promotionVehicleCategoryMappingsRecord.getUpdatedBy());
        log.setCreatedOn(promotionVehicleCategoryMappingsRecord.getCreatedOn());
        log.setUpdatedOn(promotionVehicleCategoryMappingsRecord.getUpdatedOn());
        log.setIsActive(promotionVehicleCategoryMappingsRecord.getIsActive());
        log.setState(promotionVehicleCategoryMappingsRecord.getState());
        log.setApiSource(promotionVehicleCategoryMappingsRecord.getApiSource());
        log.setNotes(promotionVehicleCategoryMappingsRecord.getNotes());
        return log;
    }

    @Override
    public void delete(Integer id) {
        context.update(PROMOTION_VEHICLE_CATEGORY_MAPPINGS)
                .set(PROMOTION_VEHICLE_CATEGORY_MAPPINGS.STATE, RecordState.DELETED.name())
                .where(PROMOTION_VEHICLE_CATEGORY_MAPPINGS.ID.eq(id))
                .and(PROMOTION_VEHICLE_CATEGORY_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PROMOTION_VEHICLE_CATEGORY_MAPPINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PromotionVehicleCategoryMappingDao findByUUID(String uuid) {
        return context.selectFrom(PROMOTION_VEHICLE_CATEGORY_MAPPINGS).where(PROMOTION_VEHICLE_CATEGORY_MAPPINGS.UUID.eq(uuid))
                .and(PROMOTION_VEHICLE_CATEGORY_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PromotionVehicleCategoryMappingDao.class);
    }

    @Override
    public PromotionVehicleCategoryMappingDao findById(Integer id) {
        return context.selectFrom(PROMOTION_VEHICLE_CATEGORY_MAPPINGS).where(PROMOTION_VEHICLE_CATEGORY_MAPPINGS.ID.eq(id))
                .and(PROMOTION_VEHICLE_CATEGORY_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PromotionVehicleCategoryMappingDao.class);
    }

    @Override
    public List<PromotionVehicleCategoryMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PROMOTION_VEHICLE_CATEGORY_MAPPINGS).where(PROMOTION_VEHICLE_CATEGORY_MAPPINGS.ID.in(ids)).fetchInto(PromotionVehicleCategoryMappingDao.class);
    }

    @Override
    public List<PromotionVehicleCategoryMappingDao> findAllActive() {
        return context.selectFrom(PROMOTION_VEHICLE_CATEGORY_MAPPINGS).fetchInto(PromotionVehicleCategoryMappingDao.class);
    }
}