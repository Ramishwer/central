package com.goev.central.repository.system.language.impl;

import com.goev.central.dao.system.language.SystemSupportedLanguageDao;
import com.goev.central.repository.system.language.SystemSupportedLanguageRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.SystemSupportedLanguagesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.goev.record.central.tables.SystemSupportedLanguages.SYSTEM_SUPPORTED_LANGUAGES;

@Slf4j
@Repository
@AllArgsConstructor
public class SystemSupportedLanguageRepositoryImpl implements SystemSupportedLanguageRepository {
    private final DSLContext context;

    @Override
    public SystemSupportedLanguageDao save(SystemSupportedLanguageDao supportedLanguage) {
        SystemSupportedLanguagesRecord systemSupportedLanguagesRecord = context.newRecord(SYSTEM_SUPPORTED_LANGUAGES, supportedLanguage);
        systemSupportedLanguagesRecord.store();
        supportedLanguage.setId(systemSupportedLanguagesRecord.getId());
        supportedLanguage.setUuid(systemSupportedLanguagesRecord.getUuid());
        return supportedLanguage;
    }

    @Override
    public SystemSupportedLanguageDao update(SystemSupportedLanguageDao supportedLanguage) {
        SystemSupportedLanguagesRecord systemSupportedLanguagesRecord = context.newRecord(SYSTEM_SUPPORTED_LANGUAGES, supportedLanguage);
        systemSupportedLanguagesRecord.update();
        return supportedLanguage;
    }

    @Override
    public void delete(Integer id) {
        context.update(SYSTEM_SUPPORTED_LANGUAGES).set(SYSTEM_SUPPORTED_LANGUAGES.STATE, RecordState.DELETED.name()).where(SYSTEM_SUPPORTED_LANGUAGES.ID.eq(id)).execute();
    }

    @Override
    public SystemSupportedLanguageDao findByUUID(String uuid) {
        return context.selectFrom(SYSTEM_SUPPORTED_LANGUAGES).where(SYSTEM_SUPPORTED_LANGUAGES.UUID.eq(uuid)).fetchAnyInto(SystemSupportedLanguageDao.class);
    }

    @Override
    public SystemSupportedLanguageDao findById(Integer id) {
        return context.selectFrom(SYSTEM_SUPPORTED_LANGUAGES).where(SYSTEM_SUPPORTED_LANGUAGES.ID.eq(id)).fetchAnyInto(SystemSupportedLanguageDao.class);
    }

    @Override
    public List<SystemSupportedLanguageDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(SYSTEM_SUPPORTED_LANGUAGES).where(SYSTEM_SUPPORTED_LANGUAGES.ID.in(ids)).fetchInto(SystemSupportedLanguageDao.class);
    }

    @Override
    public List<SystemSupportedLanguageDao> findAll() {
        return context.selectFrom(SYSTEM_SUPPORTED_LANGUAGES).fetchInto(SystemSupportedLanguageDao.class);
    }
}