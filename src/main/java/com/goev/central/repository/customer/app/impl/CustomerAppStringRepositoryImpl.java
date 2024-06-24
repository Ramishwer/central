package com.goev.central.repository.customer.app.impl;

import com.goev.central.dao.customer.app.CustomerAppStringDao;
import com.goev.central.repository.customer.app.CustomerAppStringRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerAppStringsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerAppStrings.CUSTOMER_APP_STRINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerAppStringRepositoryImpl implements CustomerAppStringRepository {

    private final DSLContext context;

    @Override
    public CustomerAppStringDao save(CustomerAppStringDao customerAppString) {
        CustomerAppStringsRecord customerAppStringsRecord = context.newRecord(CUSTOMER_APP_STRINGS, customerAppString);
        customerAppStringsRecord.store();
        customerAppString.setId(customerAppStringsRecord.getId());
        customerAppString.setUuid(customerAppStringsRecord.getUuid());
        customerAppString.setCreatedBy(customerAppStringsRecord.getCreatedBy());
        customerAppString.setUpdatedBy(customerAppStringsRecord.getUpdatedBy());
        customerAppString.setCreatedOn(customerAppStringsRecord.getCreatedOn());
        customerAppString.setUpdatedOn(customerAppStringsRecord.getUpdatedOn());
        customerAppString.setIsActive(customerAppStringsRecord.getIsActive());
        customerAppString.setState(customerAppStringsRecord.getState());
        customerAppString.setApiSource(customerAppStringsRecord.getApiSource());
        customerAppString.setNotes(customerAppStringsRecord.getNotes());
        return customerAppString;
    }

    @Override
    public CustomerAppStringDao update(CustomerAppStringDao customerAppString) {
        CustomerAppStringsRecord customerAppStringsRecord = context.newRecord(CUSTOMER_APP_STRINGS, customerAppString);
        customerAppStringsRecord.update();


        customerAppString.setCreatedBy(customerAppStringsRecord.getCreatedBy());
        customerAppString.setUpdatedBy(customerAppStringsRecord.getUpdatedBy());
        customerAppString.setCreatedOn(customerAppStringsRecord.getCreatedOn());
        customerAppString.setUpdatedOn(customerAppStringsRecord.getUpdatedOn());
        customerAppString.setIsActive(customerAppStringsRecord.getIsActive());
        customerAppString.setState(customerAppStringsRecord.getState());
        customerAppString.setApiSource(customerAppStringsRecord.getApiSource());
        customerAppString.setNotes(customerAppStringsRecord.getNotes());
        return customerAppString;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_APP_STRINGS)
                .set(CUSTOMER_APP_STRINGS.STATE, RecordState.DELETED.name())
                .where(CUSTOMER_APP_STRINGS.ID.eq(id))
                .and(CUSTOMER_APP_STRINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(CUSTOMER_APP_STRINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public CustomerAppStringDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_APP_STRINGS).where(CUSTOMER_APP_STRINGS.UUID.eq(uuid))
                .and(CUSTOMER_APP_STRINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerAppStringDao.class);
    }

    @Override
    public CustomerAppStringDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_APP_STRINGS).where(CUSTOMER_APP_STRINGS.ID.eq(id))
                .and(CUSTOMER_APP_STRINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerAppStringDao.class);
    }

    @Override
    public List<CustomerAppStringDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_APP_STRINGS).where(CUSTOMER_APP_STRINGS.ID.in(ids)).fetchInto(CustomerAppStringDao.class);
    }

    @Override
    public List<CustomerAppStringDao> findAllActive() {
        return context.selectFrom(CUSTOMER_APP_STRINGS).fetchInto(CustomerAppStringDao.class);
    }
}
