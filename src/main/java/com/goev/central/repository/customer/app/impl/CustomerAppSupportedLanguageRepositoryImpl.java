package com.goev.central.repository.customer.app.impl;

import com.goev.central.dao.customer.app.CustomerAppSupportedLanguageDao;
import com.goev.central.repository.customer.app.CustomerAppSupportedLanguageRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerAppSupportedLanguagesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerAppSupportedLanguages.CUSTOMER_APP_SUPPORTED_LANGUAGES;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerAppSupportedLanguageRepositoryImpl implements CustomerAppSupportedLanguageRepository {

    private final DSLContext context;

    @Override
    public CustomerAppSupportedLanguageDao save(CustomerAppSupportedLanguageDao customerAppSupportedLanguage) {
        CustomerAppSupportedLanguagesRecord customerAppSupportedLanguagesRecord = context.newRecord(CUSTOMER_APP_SUPPORTED_LANGUAGES, customerAppSupportedLanguage);
        customerAppSupportedLanguagesRecord.store();
        customerAppSupportedLanguage.setId(customerAppSupportedLanguagesRecord.getId());
        customerAppSupportedLanguage.setUuid(customerAppSupportedLanguage.getUuid());
        customerAppSupportedLanguage.setCreatedBy(customerAppSupportedLanguage.getCreatedBy());
        customerAppSupportedLanguage.setUpdatedBy(customerAppSupportedLanguage.getUpdatedBy());
        customerAppSupportedLanguage.setCreatedOn(customerAppSupportedLanguage.getCreatedOn());
        customerAppSupportedLanguage.setUpdatedOn(customerAppSupportedLanguage.getUpdatedOn());
        customerAppSupportedLanguage.setIsActive(customerAppSupportedLanguage.getIsActive());
        customerAppSupportedLanguage.setState(customerAppSupportedLanguage.getState());
        customerAppSupportedLanguage.setApiSource(customerAppSupportedLanguage.getApiSource());
        customerAppSupportedLanguage.setNotes(customerAppSupportedLanguage.getNotes());
        return customerAppSupportedLanguage;
    }

    @Override
    public CustomerAppSupportedLanguageDao update(CustomerAppSupportedLanguageDao customerAppSupportedLanguage) {
        CustomerAppSupportedLanguagesRecord customerAppSupportedLanguagesRecord = context.newRecord(CUSTOMER_APP_SUPPORTED_LANGUAGES, customerAppSupportedLanguage);
        customerAppSupportedLanguagesRecord.update();


        customerAppSupportedLanguage.setCreatedBy(customerAppSupportedLanguagesRecord.getCreatedBy());
        customerAppSupportedLanguage.setUpdatedBy(customerAppSupportedLanguagesRecord.getUpdatedBy());
        customerAppSupportedLanguage.setCreatedOn(customerAppSupportedLanguagesRecord.getCreatedOn());
        customerAppSupportedLanguage.setUpdatedOn(customerAppSupportedLanguagesRecord.getUpdatedOn());
        customerAppSupportedLanguage.setIsActive(customerAppSupportedLanguagesRecord.getIsActive());
        customerAppSupportedLanguage.setState(customerAppSupportedLanguagesRecord.getState());
        customerAppSupportedLanguage.setApiSource(customerAppSupportedLanguagesRecord.getApiSource());
        customerAppSupportedLanguage.setNotes(customerAppSupportedLanguagesRecord.getNotes());
        return customerAppSupportedLanguage;
    }

    @Override
    public void delete(Integer id) {
     context.update(CUSTOMER_APP_SUPPORTED_LANGUAGES)
     .set(CUSTOMER_APP_SUPPORTED_LANGUAGES.STATE,RecordState.DELETED.name())
     .where(CUSTOMER_APP_SUPPORTED_LANGUAGES.ID.eq(id))
     .and(CUSTOMER_APP_SUPPORTED_LANGUAGES.STATE.eq(RecordState.ACTIVE.name()))
     .and(CUSTOMER_APP_SUPPORTED_LANGUAGES.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public CustomerAppSupportedLanguageDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_APP_SUPPORTED_LANGUAGES).where(CUSTOMER_APP_SUPPORTED_LANGUAGES.UUID.eq(uuid))
                .and(CUSTOMER_APP_SUPPORTED_LANGUAGES.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerAppSupportedLanguageDao.class);
    }

    @Override
    public CustomerAppSupportedLanguageDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_APP_SUPPORTED_LANGUAGES).where(CUSTOMER_APP_SUPPORTED_LANGUAGES.ID.eq(id))
                .and(CUSTOMER_APP_SUPPORTED_LANGUAGES.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerAppSupportedLanguageDao.class);
    }

    @Override
    public List<CustomerAppSupportedLanguageDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_APP_SUPPORTED_LANGUAGES).where(CUSTOMER_APP_SUPPORTED_LANGUAGES.ID.in(ids)).fetchInto(CustomerAppSupportedLanguageDao.class);
    }

    @Override
    public List<CustomerAppSupportedLanguageDao> findAllActive() {
        return context.selectFrom(CUSTOMER_APP_SUPPORTED_LANGUAGES).fetchInto(CustomerAppSupportedLanguageDao.class);
    }
}
