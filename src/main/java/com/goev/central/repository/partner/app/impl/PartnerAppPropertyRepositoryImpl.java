package com.goev.central.repository.partner.app.impl;

import com.goev.central.dao.partner.app.PartnerAppPropertyDao;
import com.goev.central.repository.partner.app.PartnerAppPropertyRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerAppPropertiesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerAppProperties.PARTNER_APP_PROPERTIES;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerAppPropertyRepositoryImpl implements PartnerAppPropertyRepository {

    private final DSLContext context;

    @Override
    public PartnerAppPropertyDao save(PartnerAppPropertyDao partnerAppProperty) {
        PartnerAppPropertiesRecord partnerAppPropertiesRecord = context.newRecord(PARTNER_APP_PROPERTIES, partnerAppProperty);
        partnerAppPropertiesRecord.store();
        partnerAppProperty.setId(partnerAppPropertiesRecord.getId());
        partnerAppProperty.setUuid(partnerAppPropertiesRecord.getUuid());
        partnerAppProperty.setCreatedBy(partnerAppPropertiesRecord.getCreatedBy());
        partnerAppProperty.setUpdatedBy(partnerAppPropertiesRecord.getUpdatedBy());
        partnerAppProperty.setCreatedOn(partnerAppPropertiesRecord.getCreatedOn());
        partnerAppProperty.setUpdatedOn(partnerAppPropertiesRecord.getUpdatedOn());
        partnerAppProperty.setIsActive(partnerAppPropertiesRecord.getIsActive());
        partnerAppProperty.setState(partnerAppPropertiesRecord.getState());
        partnerAppProperty.setApiSource(partnerAppPropertiesRecord.getApiSource());
        partnerAppProperty.setNotes(partnerAppPropertiesRecord.getNotes());
        return partnerAppProperty;
    }

    @Override
    public PartnerAppPropertyDao update(PartnerAppPropertyDao partnerAppProperty) {
        PartnerAppPropertiesRecord partnerAppPropertiesRecord = context.newRecord(PARTNER_APP_PROPERTIES, partnerAppProperty);
        partnerAppPropertiesRecord.update();


        partnerAppProperty.setCreatedBy(partnerAppPropertiesRecord.getCreatedBy());
        partnerAppProperty.setUpdatedBy(partnerAppPropertiesRecord.getUpdatedBy());
        partnerAppProperty.setCreatedOn(partnerAppPropertiesRecord.getCreatedOn());
        partnerAppProperty.setUpdatedOn(partnerAppPropertiesRecord.getUpdatedOn());
        partnerAppProperty.setIsActive(partnerAppPropertiesRecord.getIsActive());
        partnerAppProperty.setState(partnerAppPropertiesRecord.getState());
        partnerAppProperty.setApiSource(partnerAppPropertiesRecord.getApiSource());
        partnerAppProperty.setNotes(partnerAppPropertiesRecord.getNotes());
        return partnerAppProperty;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_APP_PROPERTIES)
                .set(PARTNER_APP_PROPERTIES.STATE, RecordState.DELETED.name())
                .where(PARTNER_APP_PROPERTIES.ID.eq(id))
                .and(PARTNER_APP_PROPERTIES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_APP_PROPERTIES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerAppPropertyDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_APP_PROPERTIES).where(PARTNER_APP_PROPERTIES.UUID.eq(uuid))
                .and(PARTNER_APP_PROPERTIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerAppPropertyDao.class);
    }

    @Override
    public PartnerAppPropertyDao findById(Integer id) {
        return context.selectFrom(PARTNER_APP_PROPERTIES).where(PARTNER_APP_PROPERTIES.ID.eq(id))
                .and(PARTNER_APP_PROPERTIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerAppPropertyDao.class);
    }

    @Override
    public List<PartnerAppPropertyDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_APP_PROPERTIES).where(PARTNER_APP_PROPERTIES.ID.in(ids)).fetchInto(PartnerAppPropertyDao.class);
    }

    @Override
    public List<PartnerAppPropertyDao> findAllActive() {
        return context.selectFrom(PARTNER_APP_PROPERTIES).fetchInto(PartnerAppPropertyDao.class);
    }
}
