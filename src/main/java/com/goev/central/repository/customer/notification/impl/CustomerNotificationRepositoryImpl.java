package com.goev.central.repository.customer.notification.impl;

import com.goev.central.dao.customer.notification.CustomerNotificationDao;
import com.goev.central.repository.customer.notification.CustomerNotificationRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerNotificationsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerNotifications.CUSTOMER_NOTIFICATIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerNotificationRepositoryImpl implements CustomerNotificationRepository {

    private final DSLContext context;

    @Override
    public CustomerNotificationDao save(CustomerNotificationDao customerNotification) {
        CustomerNotificationsRecord customerNotificationsRecord = context.newRecord(CUSTOMER_NOTIFICATIONS, customerNotification);
        customerNotificationsRecord.store();
        customerNotification.setId(customerNotificationsRecord.getId());
        customerNotification.setUuid(customerNotification.getUuid());
        return customerNotification;
    }

    @Override
    public CustomerNotificationDao update(CustomerNotificationDao customerNotification) {
        CustomerNotificationsRecord customerNotificationsRecord = context.newRecord(CUSTOMER_NOTIFICATIONS, customerNotification);
        customerNotificationsRecord.update();
        return customerNotification;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_NOTIFICATIONS).set(CUSTOMER_NOTIFICATIONS.STATE, RecordState.DELETED.name()).where(CUSTOMER_NOTIFICATIONS.ID.eq(id)).execute();
    }

    @Override
    public CustomerNotificationDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_NOTIFICATIONS).where(CUSTOMER_NOTIFICATIONS.UUID.eq(uuid)).fetchAnyInto(CustomerNotificationDao.class);
    }

    @Override
    public CustomerNotificationDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_NOTIFICATIONS).where(CUSTOMER_NOTIFICATIONS.ID.eq(id)).fetchAnyInto(CustomerNotificationDao.class);
    }

    @Override
    public List<CustomerNotificationDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_NOTIFICATIONS).where(CUSTOMER_NOTIFICATIONS.ID.in(ids)).fetchInto(CustomerNotificationDao.class);
    }

    @Override
    public List<CustomerNotificationDao> findAll() {
        return context.selectFrom(CUSTOMER_NOTIFICATIONS).fetchInto(CustomerNotificationDao.class);
    }
}
