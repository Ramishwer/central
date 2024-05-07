package com.goev.central.repository.customer.app.impl;

import com.goev.central.dao.customer.app.CustomerAppPropertyDao;
import com.goev.central.repository.customer.app.CustomerAppPropertyRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerAppPropertiesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerAppProperties.CUSTOMER_APP_PROPERTIES;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerAppPropertyRepositoryImpl implements CustomerAppPropertyRepository {

    private final DSLContext context;

    @Override
    public CustomerAppPropertyDao save(CustomerAppPropertyDao customerAppProperty) {
        CustomerAppPropertiesRecord customerAppPropertiesRecord = context.newRecord(CUSTOMER_APP_PROPERTIES, customerAppProperty);
        customerAppPropertiesRecord.store();
        customerAppProperty.setId(customerAppPropertiesRecord.getId());
        customerAppProperty.setUuid(customerAppProperty.getUuid());
        return customerAppProperty;
    }

    @Override
    public CustomerAppPropertyDao update(CustomerAppPropertyDao customerAppProperty) {
        CustomerAppPropertiesRecord customerAppPropertiesRecord = context.newRecord(CUSTOMER_APP_PROPERTIES, customerAppProperty);
        customerAppPropertiesRecord.update();
        return customerAppProperty;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_APP_PROPERTIES).set(CUSTOMER_APP_PROPERTIES.STATE, RecordState.DELETED.name()).where(CUSTOMER_APP_PROPERTIES.ID.eq(id)).execute();
    }

    @Override
    public CustomerAppPropertyDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_APP_PROPERTIES).where(CUSTOMER_APP_PROPERTIES.UUID.eq(uuid)).fetchAnyInto(CustomerAppPropertyDao.class);
    }

    @Override
    public CustomerAppPropertyDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_APP_PROPERTIES).where(CUSTOMER_APP_PROPERTIES.ID.eq(id)).fetchAnyInto(CustomerAppPropertyDao.class);
    }

    @Override
    public List<CustomerAppPropertyDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_APP_PROPERTIES).where(CUSTOMER_APP_PROPERTIES.ID.in(ids)).fetchInto(CustomerAppPropertyDao.class);
    }

    @Override
    public List<CustomerAppPropertyDao> findAll() {
        return context.selectFrom(CUSTOMER_APP_PROPERTIES).fetchInto(CustomerAppPropertyDao.class);
    }
}
