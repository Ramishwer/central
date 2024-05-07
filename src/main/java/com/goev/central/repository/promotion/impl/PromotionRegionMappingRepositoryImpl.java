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
import java.util.Map;

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
        return log;
    }

    @Override
    public PromotionRegionMappingDao update(PromotionRegionMappingDao log) {
        PromotionRegionMappingsRecord promotionRegionMappingsRecord = context.newRecord(PROMOTION_REGION_MAPPINGS, log);
        promotionRegionMappingsRecord.update();
        return log;
    }

    @Override
    public void delete(Integer id) {
        context.update(PROMOTION_REGION_MAPPINGS).set(PROMOTION_REGION_MAPPINGS.STATE, RecordState.DELETED.name()).where(PROMOTION_REGION_MAPPINGS.ID.eq(id)).execute();
    }

    @Override
    public PromotionRegionMappingDao findByUUID(String uuid) {
        return context.selectFrom(PROMOTION_REGION_MAPPINGS).where(PROMOTION_REGION_MAPPINGS.UUID.eq(uuid)).fetchAnyInto(PromotionRegionMappingDao.class);
    }

    @Override
    public PromotionRegionMappingDao findById(Integer id) {
        return context.selectFrom(PROMOTION_REGION_MAPPINGS).where(PROMOTION_REGION_MAPPINGS.ID.eq(id)).fetchAnyInto(PromotionRegionMappingDao.class);
    }

    @Override
    public List<PromotionRegionMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PROMOTION_REGION_MAPPINGS).where(PROMOTION_REGION_MAPPINGS.ID.in(ids)).fetchInto(PromotionRegionMappingDao.class);
    }

    @Override
    public List<PromotionRegionMappingDao> findAll() {
        return context.selectFrom(PROMOTION_REGION_MAPPINGS).fetchInto(PromotionRegionMappingDao.class);
    }
}