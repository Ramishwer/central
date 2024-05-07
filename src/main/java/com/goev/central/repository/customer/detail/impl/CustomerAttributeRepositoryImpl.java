package com.goev.central.repository.customer.detail.impl;

import com.goev.central.dao.customer.detail.CustomerAttributeDao;
import com.goev.central.repository.customer.detail.CustomerAttributeRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerAttributesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerAttributes.CUSTOMER_ATTRIBUTES;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerAttributeRepositoryImpl implements CustomerAttributeRepository {

    private final DSLContext context;

    @Override
    public CustomerAttributeDao save(CustomerAttributeDao customerAttribute) {
        CustomerAttributesRecord customerAttributesRecord = context.newRecord(CUSTOMER_ATTRIBUTES, customerAttribute);
        customerAttributesRecord.store();
        customerAttribute.setId(customerAttributesRecord.getId());
        customerAttribute.setUuid(customerAttribute.getUuid());
        return customerAttribute;
    }

    @Override
    public CustomerAttributeDao update(CustomerAttributeDao customerAttribute) {
        CustomerAttributesRecord customerAttributesRecord = context.newRecord(CUSTOMER_ATTRIBUTES, customerAttribute);
        customerAttributesRecord.update();
        return customerAttribute;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_ATTRIBUTES).set(CUSTOMER_ATTRIBUTES.STATE, RecordState.DELETED.name()).where(CUSTOMER_ATTRIBUTES.ID.eq(id)).execute();
    }

    @Override
    public CustomerAttributeDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_ATTRIBUTES).where(CUSTOMER_ATTRIBUTES.UUID.eq(uuid)).fetchAnyInto(CustomerAttributeDao.class);
    }

    @Override
    public CustomerAttributeDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_ATTRIBUTES).where(CUSTOMER_ATTRIBUTES.ID.eq(id)).fetchAnyInto(CustomerAttributeDao.class);
    }

    @Override
    public List<CustomerAttributeDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_ATTRIBUTES).where(CUSTOMER_ATTRIBUTES.ID.in(ids)).fetchInto(CustomerAttributeDao.class);
    }

    @Override
    public List<CustomerAttributeDao> findAll() {
        return context.selectFrom(CUSTOMER_ATTRIBUTES).fetchInto(CustomerAttributeDao.class);
    }
}
