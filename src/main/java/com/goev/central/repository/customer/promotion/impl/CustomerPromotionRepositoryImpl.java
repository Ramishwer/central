package com.goev.central.repository.customer.promotion.impl;

import com.goev.central.dao.customer.promotion.CustomerPromotionDao;
import com.goev.central.repository.customer.promotion.CustomerPromotionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerPromotionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerPromotions.CUSTOMER_PROMOTIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerPromotionRepositoryImpl implements CustomerPromotionRepository {

    private final DSLContext context;

    @Override
    public CustomerPromotionDao save(CustomerPromotionDao customerPromotion) {
        CustomerPromotionsRecord customerPromotionsRecord = context.newRecord(CUSTOMER_PROMOTIONS, customerPromotion);
        customerPromotionsRecord.store();
        customerPromotion.setId(customerPromotionsRecord.getId());
        customerPromotion.setUuid(customerPromotion.getUuid());
        customerPromotion.setCreatedBy(customerPromotion.getCreatedBy());
        customerPromotion.setUpdatedBy(customerPromotion.getUpdatedBy());
        customerPromotion.setCreatedOn(customerPromotion.getCreatedOn());
        customerPromotion.setUpdatedOn(customerPromotion.getUpdatedOn());
        customerPromotion.setIsActive(customerPromotion.getIsActive());
        customerPromotion.setState(customerPromotion.getState());
        customerPromotion.setApiSource(customerPromotion.getApiSource());
        customerPromotion.setNotes(customerPromotion.getNotes());
        return customerPromotion;
    }

    @Override
    public CustomerPromotionDao update(CustomerPromotionDao customerPromotion) {
        CustomerPromotionsRecord customerPromotionsRecord = context.newRecord(CUSTOMER_PROMOTIONS, customerPromotion);
        customerPromotionsRecord.update();


        customerPromotion.setCreatedBy(customerPromotionsRecord.getCreatedBy());
        customerPromotion.setUpdatedBy(customerPromotionsRecord.getUpdatedBy());
        customerPromotion.setCreatedOn(customerPromotionsRecord.getCreatedOn());
        customerPromotion.setUpdatedOn(customerPromotionsRecord.getUpdatedOn());
        customerPromotion.setIsActive(customerPromotionsRecord.getIsActive());
        customerPromotion.setState(customerPromotionsRecord.getState());
        customerPromotion.setApiSource(customerPromotionsRecord.getApiSource());
        customerPromotion.setNotes(customerPromotionsRecord.getNotes());
        return customerPromotion;
    }

    @Override
    public void delete(Integer id) {
     context.update(CUSTOMER_PROMOTIONS)
     .set(CUSTOMER_PROMOTIONS.STATE,RecordState.DELETED.name())
     .where(CUSTOMER_PROMOTIONS.ID.eq(id))
     .and(CUSTOMER_PROMOTIONS.STATE.eq(RecordState.ACTIVE.name()))
     .and(CUSTOMER_PROMOTIONS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public CustomerPromotionDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_PROMOTIONS).where(CUSTOMER_PROMOTIONS.UUID.eq(uuid))
                .and(CUSTOMER_PROMOTIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerPromotionDao.class);
    }

    @Override
    public CustomerPromotionDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_PROMOTIONS).where(CUSTOMER_PROMOTIONS.ID.eq(id))
                .and(CUSTOMER_PROMOTIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerPromotionDao.class);
    }

    @Override
    public List<CustomerPromotionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_PROMOTIONS).where(CUSTOMER_PROMOTIONS.ID.in(ids)).fetchInto(CustomerPromotionDao.class);
    }

    @Override
    public List<CustomerPromotionDao> findAllActive() {
        return context.selectFrom(CUSTOMER_PROMOTIONS).fetchInto(CustomerPromotionDao.class);
    }
}
