package com.goev.central.repository.payout.impl;

import com.goev.central.dao.payout.PayoutCategoryDao;
import com.goev.central.repository.payout.PayoutCategoryRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PayoutCategoriesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PayoutCategories.PAYOUT_CATEGORIES;

@Slf4j
@Repository
@AllArgsConstructor
public class PayoutCategoryRepositoryImpl implements PayoutCategoryRepository {

    private final DSLContext context;

    @Override
    public PayoutCategoryDao save(PayoutCategoryDao payoutCategory) {
        PayoutCategoriesRecord payoutCategoriesRecord = context.newRecord(PAYOUT_CATEGORIES, payoutCategory);
        payoutCategoriesRecord.store();
        payoutCategory.setId(payoutCategoriesRecord.getId());
        payoutCategory.setUuid(payoutCategoriesRecord.getUuid());
        payoutCategory.setCreatedBy(payoutCategoriesRecord.getCreatedBy());
        payoutCategory.setUpdatedBy(payoutCategoriesRecord.getUpdatedBy());
        payoutCategory.setCreatedOn(payoutCategoriesRecord.getCreatedOn());
        payoutCategory.setUpdatedOn(payoutCategoriesRecord.getUpdatedOn());
        payoutCategory.setIsActive(payoutCategoriesRecord.getIsActive());
        payoutCategory.setState(payoutCategoriesRecord.getState());
        payoutCategory.setApiSource(payoutCategoriesRecord.getApiSource());
        payoutCategory.setNotes(payoutCategoriesRecord.getNotes());
        return payoutCategory;
    }

    @Override
    public PayoutCategoryDao update(PayoutCategoryDao payoutCategory) {
        PayoutCategoriesRecord payoutCategoriesRecord = context.newRecord(PAYOUT_CATEGORIES, payoutCategory);
        payoutCategoriesRecord.update();


        payoutCategory.setCreatedBy(payoutCategoriesRecord.getCreatedBy());
        payoutCategory.setUpdatedBy(payoutCategoriesRecord.getUpdatedBy());
        payoutCategory.setCreatedOn(payoutCategoriesRecord.getCreatedOn());
        payoutCategory.setUpdatedOn(payoutCategoriesRecord.getUpdatedOn());
        payoutCategory.setIsActive(payoutCategoriesRecord.getIsActive());
        payoutCategory.setState(payoutCategoriesRecord.getState());
        payoutCategory.setApiSource(payoutCategoriesRecord.getApiSource());
        payoutCategory.setNotes(payoutCategoriesRecord.getNotes());
        return payoutCategory;
    }

    @Override
    public void delete(Integer id) {
        context.update(PAYOUT_CATEGORIES)
                .set(PAYOUT_CATEGORIES.STATE, RecordState.DELETED.name())
                .where(PAYOUT_CATEGORIES.ID.eq(id))
                .and(PAYOUT_CATEGORIES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PAYOUT_CATEGORIES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PayoutCategoryDao findByUUID(String uuid) {
        return context.selectFrom(PAYOUT_CATEGORIES).where(PAYOUT_CATEGORIES.UUID.eq(uuid))
                .and(PAYOUT_CATEGORIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PayoutCategoryDao.class);
    }

    @Override
    public PayoutCategoryDao findById(Integer id) {
        return context.selectFrom(PAYOUT_CATEGORIES).where(PAYOUT_CATEGORIES.ID.eq(id))
                .and(PAYOUT_CATEGORIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PayoutCategoryDao.class);
    }

    @Override
    public List<PayoutCategoryDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PAYOUT_CATEGORIES).where(PAYOUT_CATEGORIES.ID.in(ids)).fetchInto(PayoutCategoryDao.class);
    }

    @Override
    public List<PayoutCategoryDao> findAllActive() {
        return context.selectFrom(PAYOUT_CATEGORIES).fetchInto(PayoutCategoryDao.class);
    }

}
