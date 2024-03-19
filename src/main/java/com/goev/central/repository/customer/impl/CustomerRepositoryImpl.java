package com.goev.central.repository.customer.impl;

import com.goev.central.dao.customer.CustomerDao;
import com.goev.central.repository.customer.CustomerRepository;
import com.goev.record.central.tables.records.CustomersRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Customers.CUSTOMERS;

@Slf4j
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private DSLContext context;
    @Override
    public CustomerDao save(CustomerDao customer) {
        CustomersRecord record =  context.newRecord(CUSTOMERS,customer);
        record.store();
        customer.setId(record.getId());
        return customer;
    }

    @Override
    public CustomerDao update(CustomerDao customer) {
        CustomersRecord record =  context.newRecord(CUSTOMERS,customer);
        record.update();
        return customer;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMERS).set(CUSTOMERS.STATE,"DELETED").where(CUSTOMERS.ID.eq(id)).execute();
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
}
