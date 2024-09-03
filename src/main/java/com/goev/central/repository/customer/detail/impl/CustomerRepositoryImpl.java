package com.goev.central.repository.customer.detail.impl;

import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.repository.customer.detail.CustomerRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
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
    private final EventExecutorUtils eventExecutor;

    @Override
    public CustomerDao save(CustomerDao customer) {
        CustomersRecord customersRecord = context.newRecord(CUSTOMERS, customer);
        customersRecord.store();
        customer.setId(customersRecord.getId());
        customer.setUuid(customersRecord.getUuid());
        customer.setCreatedBy(customersRecord.getCreatedBy());
        customer.setUpdatedBy(customersRecord.getUpdatedBy());
        customer.setCreatedOn(customersRecord.getCreatedOn());
        customer.setUpdatedOn(customersRecord.getUpdatedOn());
        customer.setIsActive(customersRecord.getIsActive());
        customer.setState(customersRecord.getState());
        customer.setApiSource(customersRecord.getApiSource());
        customer.setNotes(customersRecord.getNotes());

        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("CustomerSaveEvent", customer);
        return customer;
    }

    @Override
    public CustomerDao update(CustomerDao customer) {
        CustomersRecord customersRecord = context.newRecord(CUSTOMERS, customer);
        customersRecord.update();


        customer.setCreatedBy(customersRecord.getCreatedBy());
        customer.setUpdatedBy(customersRecord.getUpdatedBy());
        customer.setCreatedOn(customersRecord.getCreatedOn());
        customer.setUpdatedOn(customersRecord.getUpdatedOn());
        customer.setIsActive(customersRecord.getIsActive());
        customer.setState(customersRecord.getState());
        customer.setApiSource(customersRecord.getApiSource());
        customer.setNotes(customersRecord.getNotes());

        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("CustomerUpdateEvent", customer);
        return customer;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMERS)
                .set(CUSTOMERS.STATE, RecordState.DELETED.name())
                .where(CUSTOMERS.ID.eq(id))
                .and(CUSTOMERS.STATE.eq(RecordState.ACTIVE.name()))
                .and(CUSTOMERS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public CustomerDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMERS).where(CUSTOMERS.UUID.eq(uuid))
                .and(CUSTOMERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerDao.class);
    }

    @Override
    public CustomerDao findById(Integer id) {
        return context.selectFrom(CUSTOMERS).where(CUSTOMERS.ID.eq(id))
                .and(CUSTOMERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerDao.class);
    }

    @Override
    public List<CustomerDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMERS)
                .where(CUSTOMERS.ID.in(ids))
                .and(CUSTOMERS.IS_ACTIVE.eq(true))
                .fetchInto(CustomerDao.class);
    }

    @Override
    public List<CustomerDao> findAllActive(String onboardingStatus) {
        return context.selectFrom(CUSTOMERS)
                .where(CUSTOMERS.ONBOARDING_STATUS.eq(onboardingStatus))
                .and(CUSTOMERS.STATE.eq(RecordState.ACTIVE.name()))
                .and(CUSTOMERS.IS_ACTIVE.eq(true))
                .fetchInto(CustomerDao.class);
    }

    @Override
    public CustomerDao findByPhoneNumber(String phoneNumber) {
        return context.selectFrom(CUSTOMERS).where(CUSTOMERS.PHONE_NUMBER.eq(phoneNumber)).fetchAnyInto(CustomerDao.class);
    }
}
