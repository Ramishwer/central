package com.goev.central.repository.promotion.impl;

import com.goev.central.dao.promotion.PromotionDao;
import com.goev.central.repository.promotion.PromotionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PromotionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.goev.record.central.tables.Promotions.PROMOTIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class PromotionRepositoryImpl implements PromotionRepository {
    private final DSLContext context;

    @Override
    public PromotionDao save(PromotionDao log) {
        PromotionsRecord promotionsRecord = context.newRecord(PROMOTIONS, log);
        promotionsRecord.store();
        log.setId(promotionsRecord.getId());
        log.setUuid(promotionsRecord.getUuid());
        return log;
    }

    @Override
    public PromotionDao update(PromotionDao log) {
        PromotionsRecord promotionsRecord = context.newRecord(PROMOTIONS, log);
        promotionsRecord.update();
        return log;
    }

    @Override
    public void delete(Integer id) {
        context.update(PROMOTIONS).set(PROMOTIONS.STATE, RecordState.DELETED.name()).where(PROMOTIONS.ID.eq(id)).execute();
    }

    @Override
    public PromotionDao findByUUID(String uuid) {
        return context.selectFrom(PROMOTIONS).where(PROMOTIONS.UUID.eq(uuid)).fetchAnyInto(PromotionDao.class);
    }

    @Override
    public PromotionDao findById(Integer id) {
        return context.selectFrom(PROMOTIONS).where(PROMOTIONS.ID.eq(id)).fetchAnyInto(PromotionDao.class);
    }

    @Override
    public List<PromotionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PROMOTIONS).where(PROMOTIONS.ID.in(ids)).fetchInto(PromotionDao.class);
    }

    @Override
    public List<PromotionDao> findAll() {
        return context.selectFrom(PROMOTIONS).fetchInto(PromotionDao.class);
    }
}