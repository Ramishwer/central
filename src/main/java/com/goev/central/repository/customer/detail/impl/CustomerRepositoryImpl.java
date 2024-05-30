package com.goev.central.repository.customer.detail.impl;

import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.repository.customer.detail.CustomerRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomersRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Customers.CUSTOMERS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final DSLContext context;

    @Override
    public CustomerDao save(CustomerDao customer) {
        CustomersRecord customersRecord = context.newRecord(CUSTOMERS, customer);
        customersRecord.store();
        customer.setId(customersRecord.getId());
        customer.setUuid(customersRecord.getUuid());
        return customer;
    }

    @Override
    public CustomerDao update(CustomerDao customer) {
        CustomersRecord customersRecord = context.newRecord(CUSTOMERS, customer);
        customersRecord.update();
        return customer;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMERS).set(CUSTOMERS.STATE, RecordState.DELETED.name()).where(CUSTOMERS.ID.eq(id)).execute();
    }

    @Override
    public CustomerDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMERS).where(CUSTOMERS.UUID.eq(uuid)).fetchAnyInto(CustomerDao.class);
    }

    @Override
    public CustomerDao findById(Integer id) {
        return context.selectFrom(CUSTOMERS).where(CUSTOMERS.ID.eq(id)).fetchAnyInto(CustomerDao.class);
    }

    @Override
    public List<CustomerDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMERS).where(CUSTOMERS.ID.in(ids)).fetchInto(CustomerDao.class);
    }

    @Override
    public List<CustomerDao> findAll() {
        return context.selectFrom(CUSTOMERS).fetchInto(CustomerDao.class);
    }

    @Override
    public CustomerDao findByPhoneNumber(String phoneNumber) {
        return context.selectFrom(CUSTOMERS).where(CUSTOMERS.PHONE_NUMBER.eq(phoneNumber)).fetchAnyInto(CustomerDao.class);
    }
}
