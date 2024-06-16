package com.goev.central.repository.partner.app.impl;

import com.goev.central.dao.partner.app.PartnerAppSupportedLanguageDao;
import com.goev.central.repository.partner.app.PartnerAppSupportedLanguageRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerAppSupportedLanguagesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerAppSupportedLanguages.PARTNER_APP_SUPPORTED_LANGUAGES;

@Repository
@AllArgsConstructor
@Slf4j
public class PartnerAppSupportedLanguageRepositoryImpl implements PartnerAppSupportedLanguageRepository {
    private final DSLContext context;

    @Override
    public PartnerAppSupportedLanguageDao save(PartnerAppSupportedLanguageDao partnerAppSupportedLanguage) {
        PartnerAppSupportedLanguagesRecord partnerAppSupportedLanguagesRecord = context.newRecord(PARTNER_APP_SUPPORTED_LANGUAGES, partnerAppSupportedLanguage);
        partnerAppSupportedLanguagesRecord.store();
        partnerAppSupportedLanguage.setId(partnerAppSupportedLanguagesRecord.getId());
        partnerAppSupportedLanguage.setUuid(partnerAppSupportedLanguagesRecord.getUuid());
        partnerAppSupportedLanguage.setCreatedBy(partnerAppSupportedLanguagesRecord.getCreatedBy());
        partnerAppSupportedLanguage.setUpdatedBy(partnerAppSupportedLanguagesRecord.getUpdatedBy());
        partnerAppSupportedLanguage.setCreatedOn(partnerAppSupportedLanguagesRecord.getCreatedOn());
        partnerAppSupportedLanguage.setUpdatedOn(partnerAppSupportedLanguagesRecord.getUpdatedOn());
        partnerAppSupportedLanguage.setIsActive(partnerAppSupportedLanguagesRecord.getIsActive());
        partnerAppSupportedLanguage.setState(partnerAppSupportedLanguagesRecord.getState());
        partnerAppSupportedLanguage.setApiSource(partnerAppSupportedLanguagesRecord.getApiSource());
        partnerAppSupportedLanguage.setNotes(partnerAppSupportedLanguagesRecord.getNotes());
        return partnerAppSupportedLanguage;
    }

    @Override
    public PartnerAppSupportedLanguageDao update(PartnerAppSupportedLanguageDao partnerAppSupportedLanguage) {
        PartnerAppSupportedLanguagesRecord partnerAppSupportedLanguagesRecord = context.newRecord(PARTNER_APP_SUPPORTED_LANGUAGES, partnerAppSupportedLanguage);
        partnerAppSupportedLanguagesRecord.update();


        partnerAppSupportedLanguage.setCreatedBy(partnerAppSupportedLanguagesRecord.getCreatedBy());
        partnerAppSupportedLanguage.setUpdatedBy(partnerAppSupportedLanguagesRecord.getUpdatedBy());
        partnerAppSupportedLanguage.setCreatedOn(partnerAppSupportedLanguagesRecord.getCreatedOn());
        partnerAppSupportedLanguage.setUpdatedOn(partnerAppSupportedLanguagesRecord.getUpdatedOn());
        partnerAppSupportedLanguage.setIsActive(partnerAppSupportedLanguagesRecord.getIsActive());
        partnerAppSupportedLanguage.setState(partnerAppSupportedLanguagesRecord.getState());
        partnerAppSupportedLanguage.setApiSource(partnerAppSupportedLanguagesRecord.getApiSource());
        partnerAppSupportedLanguage.setNotes(partnerAppSupportedLanguagesRecord.getNotes());
        return partnerAppSupportedLanguage;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_APP_SUPPORTED_LANGUAGES)
                .set(PARTNER_APP_SUPPORTED_LANGUAGES.STATE, RecordState.DELETED.name())
                .where(PARTNER_APP_SUPPORTED_LANGUAGES.ID.eq(id))
                .and(PARTNER_APP_SUPPORTED_LANGUAGES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_APP_SUPPORTED_LANGUAGES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerAppSupportedLanguageDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_APP_SUPPORTED_LANGUAGES).where(PARTNER_APP_SUPPORTED_LANGUAGES.UUID.eq(uuid))
                .and(PARTNER_APP_SUPPORTED_LANGUAGES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerAppSupportedLanguageDao.class);
    }

    @Override
    public PartnerAppSupportedLanguageDao findById(Integer id) {
        return context.selectFrom(PARTNER_APP_SUPPORTED_LANGUAGES).where(PARTNER_APP_SUPPORTED_LANGUAGES.ID.eq(id))
                .and(PARTNER_APP_SUPPORTED_LANGUAGES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerAppSupportedLanguageDao.class);
    }

    @Override
    public List<PartnerAppSupportedLanguageDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_APP_SUPPORTED_LANGUAGES).where(PARTNER_APP_SUPPORTED_LANGUAGES.ID.in(ids)).fetchInto(PartnerAppSupportedLanguageDao.class);
    }

    @Override
    public List<PartnerAppSupportedLanguageDao> findAllActive() {
        return context.selectFrom(PARTNER_APP_SUPPORTED_LANGUAGES).fetchInto(PartnerAppSupportedLanguageDao.class);
    }


}