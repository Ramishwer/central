package com.goev.central.repository.partner.notification.impl;

import com.goev.central.dao.partner.notification.PartnerNotificationTemplateDao;
import com.goev.central.repository.partner.notification.PartnerNotificationTemplateRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerNotificationTemplatesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerNotificationTemplates.PARTNER_NOTIFICATION_TEMPLATES;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerNotificationTemplateRepositoryImpl implements PartnerNotificationTemplateRepository {

    private final DSLContext context;

    @Override
    public PartnerNotificationTemplateDao save(PartnerNotificationTemplateDao partnerNotificationTemplate) {
        PartnerNotificationTemplatesRecord partnerNotificationTemplatesRecord = context.newRecord(PARTNER_NOTIFICATION_TEMPLATES, partnerNotificationTemplate);
        partnerNotificationTemplatesRecord.store();
        partnerNotificationTemplate.setId(partnerNotificationTemplatesRecord.getId());
        partnerNotificationTemplate.setUuid(partnerNotificationTemplate.getUuid());
        return partnerNotificationTemplate;
    }

    @Override
    public PartnerNotificationTemplateDao update(PartnerNotificationTemplateDao partnerNotificationTemplate) {
        PartnerNotificationTemplatesRecord partnerNotificationTemplatesRecord = context.newRecord(PARTNER_NOTIFICATION_TEMPLATES, partnerNotificationTemplate);
        partnerNotificationTemplatesRecord.update();
        return partnerNotificationTemplate;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_NOTIFICATION_TEMPLATES).set(PARTNER_NOTIFICATION_TEMPLATES.STATE, RecordState.DELETED.name()).where(PARTNER_NOTIFICATION_TEMPLATES.ID.eq(id)).execute();
    }

    @Override
    public PartnerNotificationTemplateDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_NOTIFICATION_TEMPLATES).where(PARTNER_NOTIFICATION_TEMPLATES.UUID.eq(uuid)).fetchAnyInto(PartnerNotificationTemplateDao.class);
    }

    @Override
    public PartnerNotificationTemplateDao findById(Integer id) {
        return context.selectFrom(PARTNER_NOTIFICATION_TEMPLATES).where(PARTNER_NOTIFICATION_TEMPLATES.ID.eq(id)).fetchAnyInto(PartnerNotificationTemplateDao.class);
    }

    @Override
    public List<PartnerNotificationTemplateDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_NOTIFICATION_TEMPLATES).where(PARTNER_NOTIFICATION_TEMPLATES.ID.in(ids)).fetchInto(PartnerNotificationTemplateDao.class);
    }

    @Override
    public List<PartnerNotificationTemplateDao> findAll() {
        return context.selectFrom(PARTNER_NOTIFICATION_TEMPLATES).fetchInto(PartnerNotificationTemplateDao.class);
    }
}
