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
        partnerNotification.setCreatedBy(partnerNotification.getCreatedBy());
        partnerNotification.setUpdatedBy(partnerNotification.getUpdatedBy());
        partnerNotification.setCreatedOn(partnerNotification.getCreatedOn());
        partnerNotification.setUpdatedOn(partnerNotification.getUpdatedOn());
        partnerNotification.setIsActive(partnerNotification.getIsActive());
        partnerNotification.setState(partnerNotification.getState());
        partnerNotification.setApiSource(partnerNotification.getApiSource());
        partnerNotification.setNotes(partnerNotification.getNotes());
        return partnerNotification;
    }

    @Override
    public PartnerNotificationDao update(PartnerNotificationDao partnerNotification) {
        PartnerNotificationsRecord partnerNotificationsRecord = context.newRecord(PARTNER_NOTIFICATIONS, partnerNotification);
        partnerNotificationsRecord.update();


        partnerNotification.setCreatedBy(partnerNotificationsRecord.getCreatedBy());
        partnerNotification.setUpdatedBy(partnerNotificationsRecord.getUpdatedBy());
        partnerNotification.setCreatedOn(partnerNotificationsRecord.getCreatedOn());
        partnerNotification.setUpdatedOn(partnerNotificationsRecord.getUpdatedOn());
        partnerNotification.setIsActive(partnerNotificationsRecord.getIsActive());
        partnerNotification.setState(partnerNotificationsRecord.getState());
        partnerNotification.setApiSource(partnerNotificationsRecord.getApiSource());
        partnerNotification.setNotes(partnerNotificationsRecord.getNotes());
        return partnerNotification;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_NOTIFICATIONS)
                .set(PARTNER_NOTIFICATIONS.STATE, RecordState.DELETED.name())
                .where(PARTNER_NOTIFICATIONS.ID.eq(id))
                .and(PARTNER_NOTIFICATIONS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_NOTIFICATIONS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerNotificationDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_NOTIFICATIONS).where(PARTNER_NOTIFICATIONS.UUID.eq(uuid))
                .and(PARTNER_NOTIFICATIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerNotificationDao.class);
    }

    @Override
    public PartnerNotificationDao findById(Integer id) {
        return context.selectFrom(PARTNER_NOTIFICATIONS).where(PARTNER_NOTIFICATIONS.ID.eq(id))
                .and(PARTNER_NOTIFICATIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerNotificationDao.class);
    }

    @Override
    public List<PartnerNotificationDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_NOTIFICATIONS).where(PARTNER_NOTIFICATIONS.ID.in(ids)).fetchInto(PartnerNotificationDao.class);
    }

    @Override
    public List<PartnerNotificationDao> findAllActive() {
        return context.selectFrom(PARTNER_NOTIFICATIONS).fetchInto(PartnerNotificationDao.class);
    }
}
