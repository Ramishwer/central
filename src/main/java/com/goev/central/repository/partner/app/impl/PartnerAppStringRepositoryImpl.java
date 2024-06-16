package com.goev.central.repository.partner.app.impl;

import com.goev.central.dao.partner.app.PartnerAppStringDao;
import com.goev.central.repository.partner.app.PartnerAppStringRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerAppStringsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerAppStrings.PARTNER_APP_STRINGS;

@Repository
@AllArgsConstructor
@Slf4j
public class PartnerAppStringRepositoryImpl implements PartnerAppStringRepository {
    private final DSLContext context;

    @Override
    public PartnerAppStringDao save(PartnerAppStringDao partnerAppString) {
        PartnerAppStringsRecord partnerAppStringsRecord = context.newRecord(PARTNER_APP_STRINGS, partnerAppString);
        partnerAppStringsRecord.store();
        partnerAppString.setId(partnerAppStringsRecord.getId());
        partnerAppString.setUuid(partnerAppStringsRecord.getUuid());
        partnerAppString.setCreatedBy(partnerAppStringsRecord.getCreatedBy());
        partnerAppString.setUpdatedBy(partnerAppStringsRecord.getUpdatedBy());
        partnerAppString.setCreatedOn(partnerAppStringsRecord.getCreatedOn());
        partnerAppString.setUpdatedOn(partnerAppStringsRecord.getUpdatedOn());
        partnerAppString.setIsActive(partnerAppStringsRecord.getIsActive());
        partnerAppString.setState(partnerAppStringsRecord.getState());
        partnerAppString.setApiSource(partnerAppStringsRecord.getApiSource());
        partnerAppString.setNotes(partnerAppStringsRecord.getNotes());
        return partnerAppString;
    }

    @Override
    public PartnerAppStringDao update(PartnerAppStringDao partnerAppString) {
        PartnerAppStringsRecord partnerAppStringsRecord = context.newRecord(PARTNER_APP_STRINGS, partnerAppString);
        partnerAppStringsRecord.update();


        partnerAppString.setCreatedBy(partnerAppStringsRecord.getCreatedBy());
        partnerAppString.setUpdatedBy(partnerAppStringsRecord.getUpdatedBy());
        partnerAppString.setCreatedOn(partnerAppStringsRecord.getCreatedOn());
        partnerAppString.setUpdatedOn(partnerAppStringsRecord.getUpdatedOn());
        partnerAppString.setIsActive(partnerAppStringsRecord.getIsActive());
        partnerAppString.setState(partnerAppStringsRecord.getState());
        partnerAppString.setApiSource(partnerAppStringsRecord.getApiSource());
        partnerAppString.setNotes(partnerAppStringsRecord.getNotes());
        return partnerAppString;
    }

    @Override
    public void delete(Integer id) {
     context.update(PARTNER_APP_STRINGS)
     .set(PARTNER_APP_STRINGS.STATE,RecordState.DELETED.name())
     .where(PARTNER_APP_STRINGS.ID.eq(id))
     .and(PARTNER_APP_STRINGS.STATE.eq(RecordState.ACTIVE.name()))
     .and(PARTNER_APP_STRINGS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public PartnerAppStringDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_APP_STRINGS).where(PARTNER_APP_STRINGS.UUID.eq(uuid))
                .and(PARTNER_APP_STRINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerAppStringDao.class);
    }

    @Override
    public PartnerAppStringDao findById(Integer id) {
        return context.selectFrom(PARTNER_APP_STRINGS).where(PARTNER_APP_STRINGS.ID.eq(id))
                .and(PARTNER_APP_STRINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerAppStringDao.class);
    }

    @Override
    public List<PartnerAppStringDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_APP_STRINGS).where(PARTNER_APP_STRINGS.ID.in(ids)).fetchInto(PartnerAppStringDao.class);
    }

    @Override
    public List<PartnerAppStringDao> findAllActive() {
        return context.selectFrom(PARTNER_APP_STRINGS).fetchInto(PartnerAppStringDao.class);
    }


}