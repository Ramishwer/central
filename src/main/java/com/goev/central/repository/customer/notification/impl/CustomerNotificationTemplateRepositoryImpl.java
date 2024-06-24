package com.goev.central.repository.customer.notification.impl;

import com.goev.central.dao.customer.notification.CustomerNotificationTemplateDao;
import com.goev.central.repository.customer.notification.CustomerNotificationTemplateRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerNotificationTemplatesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerNotificationTemplates.CUSTOMER_NOTIFICATION_TEMPLATES;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerNotificationTemplateRepositoryImpl implements CustomerNotificationTemplateRepository {

    private final DSLContext context;

    @Override
    public CustomerNotificationTemplateDao save(CustomerNotificationTemplateDao customerNotificationTemplate) {
        CustomerNotificationTemplatesRecord customerNotificationTemplatesRecord = context.newRecord(CUSTOMER_NOTIFICATION_TEMPLATES, customerNotificationTemplate);
        customerNotificationTemplatesRecord.store();
        customerNotificationTemplate.setId(customerNotificationTemplatesRecord.getId());
        customerNotificationTemplate.setUuid(customerNotificationTemplatesRecord.getUuid());
        customerNotificationTemplate.setCreatedBy(customerNotificationTemplatesRecord.getCreatedBy());
        customerNotificationTemplate.setUpdatedBy(customerNotificationTemplatesRecord.getUpdatedBy());
        customerNotificationTemplate.setCreatedOn(customerNotificationTemplatesRecord.getCreatedOn());
        customerNotificationTemplate.setUpdatedOn(customerNotificationTemplatesRecord.getUpdatedOn());
        customerNotificationTemplate.setIsActive(customerNotificationTemplatesRecord.getIsActive());
        customerNotificationTemplate.setState(customerNotificationTemplatesRecord.getState());
        customerNotificationTemplate.setApiSource(customerNotificationTemplatesRecord.getApiSource());
        customerNotificationTemplate.setNotes(customerNotificationTemplatesRecord.getNotes());
        return customerNotificationTemplate;
    }

    @Override
    public CustomerNotificationTemplateDao update(CustomerNotificationTemplateDao customerNotificationTemplate) {
        CustomerNotificationTemplatesRecord customerNotificationTemplatesRecord = context.newRecord(CUSTOMER_NOTIFICATION_TEMPLATES, customerNotificationTemplate);
        customerNotificationTemplatesRecord.update();


        customerNotificationTemplate.setCreatedBy(customerNotificationTemplatesRecord.getCreatedBy());
        customerNotificationTemplate.setUpdatedBy(customerNotificationTemplatesRecord.getUpdatedBy());
        customerNotificationTemplate.setCreatedOn(customerNotificationTemplatesRecord.getCreatedOn());
        customerNotificationTemplate.setUpdatedOn(customerNotificationTemplatesRecord.getUpdatedOn());
        customerNotificationTemplate.setIsActive(customerNotificationTemplatesRecord.getIsActive());
        customerNotificationTemplate.setState(customerNotificationTemplatesRecord.getState());
        customerNotificationTemplate.setApiSource(customerNotificationTemplatesRecord.getApiSource());
        customerNotificationTemplate.setNotes(customerNotificationTemplatesRecord.getNotes());
        return customerNotificationTemplate;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_NOTIFICATION_TEMPLATES)
                .set(CUSTOMER_NOTIFICATION_TEMPLATES.STATE, RecordState.DELETED.name())
                .where(CUSTOMER_NOTIFICATION_TEMPLATES.ID.eq(id))
                .and(CUSTOMER_NOTIFICATION_TEMPLATES.STATE.eq(RecordState.ACTIVE.name()))
                .and(CUSTOMER_NOTIFICATION_TEMPLATES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public CustomerNotificationTemplateDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_NOTIFICATION_TEMPLATES).where(CUSTOMER_NOTIFICATION_TEMPLATES.UUID.eq(uuid))
                .and(CUSTOMER_NOTIFICATION_TEMPLATES.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerNotificationTemplateDao.class);
    }

    @Override
    public CustomerNotificationTemplateDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_NOTIFICATION_TEMPLATES).where(CUSTOMER_NOTIFICATION_TEMPLATES.ID.eq(id))
                .and(CUSTOMER_NOTIFICATION_TEMPLATES.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerNotificationTemplateDao.class);
    }

    @Override
    public List<CustomerNotificationTemplateDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_NOTIFICATION_TEMPLATES).where(CUSTOMER_NOTIFICATION_TEMPLATES.ID.in(ids)).fetchInto(CustomerNotificationTemplateDao.class);
    }

    @Override
    public List<CustomerNotificationTemplateDao> findAllActive() {
        return context.selectFrom(CUSTOMER_NOTIFICATION_TEMPLATES).fetchInto(CustomerNotificationTemplateDao.class);
    }
}
