package com.goev.central.repository.partner.notification.impl;

import com.goev.central.dao.partner.notification.PartnerNotificationDao;
import com.goev.central.repository.partner.notification.PartnerNotificationRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerNotificationsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerNotifications.PARTNER_NOTIFICATIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerNotificationRepositoryImpl implements PartnerNotificationRepository {

    private final DSLContext context;

    @Override
    public PartnerNotificationDao save(PartnerNotificationDao partnerNotification) {
        PartnerNotificationsRecord partnerNotificationsRecord = context.newRecord(PARTNER_NOTIFICATIONS, partnerNotification);
        partnerNotificationsRecord.store();
        partnerNotification.setId(partnerNotificationsRecord.getId());
        partnerNotification.setUuid(partnerNotification.getUuid());
        return partnerNotification;
    }

    @Override
    public PartnerNotificationDao update(PartnerNotificationDao partnerNotification) {
        PartnerNotificationsRecord partnerNotificationsRecord = context.newRecord(PARTNER_NOTIFICATIONS, partnerNotification);
        partnerNotificationsRecord.update();
        return partnerNotification;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_NOTIFICATIONS).set(PARTNER_NOTIFICATIONS.STATE, RecordState.DELETED.name()).where(PARTNER_NOTIFICATIONS.ID.eq(id)).execute();
    }

    @Override
    public PartnerNotificationDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_NOTIFICATIONS).where(PARTNER_NOTIFICATIONS.UUID.eq(uuid)).fetchAnyInto(PartnerNotificationDao.class);
    }

    @Override
    public PartnerNotificationDao findById(Integer id) {
        return context.selectFrom(PARTNER_NOTIFICATIONS).where(PARTNER_NOTIFICATIONS.ID.eq(id)).fetchAnyInto(PartnerNotificationDao.class);
    }

    @Override
    public List<PartnerNotificationDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_NOTIFICATIONS).where(PARTNER_NOTIFICATIONS.ID.in(ids)).fetchInto(PartnerNotificationDao.class);
    }

    @Override
    public List<PartnerNotificationDao> findAll() {
        return context.selectFrom(PARTNER_NOTIFICATIONS).fetchInto(PartnerNotificationDao.class);
    }
}
