package com.goev.central.repository.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerCategoryDao;
import com.goev.central.repository.partner.detail.PartnerCategoryRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerCategoriesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerCategories.PARTNER_CATEGORIES;

@Repository
@AllArgsConstructor
@Slf4j
public class PartnerCategoryRepositoryImpl implements PartnerCategoryRepository {
    private final DSLContext context;

    @Override
    public PartnerCategoryDao save(PartnerCategoryDao category) {
        PartnerCategoriesRecord partnerCategoriesRecord = context.newRecord(PARTNER_CATEGORIES, category);
        partnerCategoriesRecord.store();
        category.setId(partnerCategoriesRecord.getId());
        category.setUuid(partnerCategoriesRecord.getUuid());
        category.setCreatedBy(partnerCategoriesRecord.getCreatedBy());
        category.setUpdatedBy(partnerCategoriesRecord.getUpdatedBy());
        category.setCreatedOn(partnerCategoriesRecord.getCreatedOn());
        category.setUpdatedOn(partnerCategoriesRecord.getUpdatedOn());
        category.setIsActive(partnerCategoriesRecord.getIsActive());
        category.setState(partnerCategoriesRecord.getState());
        category.setApiSource(partnerCategoriesRecord.getApiSource());
        category.setNotes(partnerCategoriesRecord.getNotes());
        return category;
    }

    @Override
    public PartnerCategoryDao update(PartnerCategoryDao category) {
        PartnerCategoriesRecord partnerCategoriesRecord = context.newRecord(PARTNER_CATEGORIES, category);
        partnerCategoriesRecord.update();


        category.setCreatedBy(partnerCategoriesRecord.getCreatedBy());
        category.setUpdatedBy(partnerCategoriesRecord.getUpdatedBy());
        category.setCreatedOn(partnerCategoriesRecord.getCreatedOn());
        category.setUpdatedOn(partnerCategoriesRecord.getUpdatedOn());
        category.setIsActive(partnerCategoriesRecord.getIsActive());
        category.setState(partnerCategoriesRecord.getState());
        category.setApiSource(partnerCategoriesRecord.getApiSource());
        category.setNotes(partnerCategoriesRecord.getNotes());
        return category;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_CATEGORIES)
                .set(PARTNER_CATEGORIES.STATE, RecordState.DELETED.name())
                .where(PARTNER_CATEGORIES.ID.eq(id))
                .and(PARTNER_CATEGORIES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_CATEGORIES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerCategoryDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_CATEGORIES).where(PARTNER_CATEGORIES.UUID.eq(uuid))
                .and(PARTNER_CATEGORIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerCategoryDao.class);
    }

    @Override
    public PartnerCategoryDao findById(Integer id) {
        return context.selectFrom(PARTNER_CATEGORIES).where(PARTNER_CATEGORIES.ID.eq(id))
                .and(PARTNER_CATEGORIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerCategoryDao.class);
    }

    @Override
    public List<PartnerCategoryDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_CATEGORIES).where(PARTNER_CATEGORIES.ID.in(ids)).fetchInto(PartnerCategoryDao.class);
    }

    @Override
    public List<PartnerCategoryDao> findAllActive() {
        return context.selectFrom(PARTNER_CATEGORIES).fetchInto(PartnerCategoryDao.class);
    }
}
