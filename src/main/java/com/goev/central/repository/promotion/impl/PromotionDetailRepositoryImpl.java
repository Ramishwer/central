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
import java.util.Map;

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
        return log;
    }

    @Override
    public PromotionDetailDao update(PromotionDetailDao log) {
        PromotionDetailsRecord promotionDetailsRecord = context.newRecord(PROMOTION_DETAILS, log);
        promotionDetailsRecord.update();
        return log;
    }

    @Override
    public void delete(Integer id) {
        context.update(PROMOTION_DETAILS).set(PROMOTION_DETAILS.STATE, RecordState.DELETED.name()).where(PROMOTION_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public PromotionDetailDao findByUUID(String uuid) {
        return context.selectFrom(PROMOTION_DETAILS).where(PROMOTION_DETAILS.UUID.eq(uuid)).fetchAnyInto(PromotionDetailDao.class);
    }

    @Override
    public PromotionDetailDao findById(Integer id) {
        return context.selectFrom(PROMOTION_DETAILS).where(PROMOTION_DETAILS.ID.eq(id)).fetchAnyInto(PromotionDetailDao.class);
    }

    @Override
    public List<PromotionDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PROMOTION_DETAILS).where(PROMOTION_DETAILS.ID.in(ids)).fetchInto(PromotionDetailDao.class);
    }

    @Override
    public List<PromotionDetailDao> findAll() {
        return context.selectFrom(PROMOTION_DETAILS).fetchInto(PromotionDetailDao.class);
    }
}