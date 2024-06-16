package com.goev.central.repository.customer.payment.impl;


import com.goev.central.dao.customer.payment.CustomerPaymentDao;
import com.goev.central.repository.customer.payment.CustomerPaymentRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerPaymentsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerPayments.CUSTOMER_PAYMENTS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerPaymentRepositoryImpl implements CustomerPaymentRepository {

    private final DSLContext context;

    @Override
    public CustomerPaymentDao save(CustomerPaymentDao customerPaymentDao) {
        CustomerPaymentsRecord customerPaymentsRecord = context.newRecord(CUSTOMER_PAYMENTS, customerPaymentDao);
        customerPaymentsRecord.store();
        customerPaymentDao.setId(customerPaymentsRecord.getId());
        customerPaymentDao.setUuid(customerPaymentsRecord.getUuid());
        customerPaymentDao.setCreatedBy(customerPaymentsRecord.getCreatedBy());
        customerPaymentDao.setUpdatedBy(customerPaymentsRecord.getUpdatedBy());
        customerPaymentDao.setCreatedOn(customerPaymentsRecord.getCreatedOn());
        customerPaymentDao.setUpdatedOn(customerPaymentsRecord.getUpdatedOn());
        customerPaymentDao.setIsActive(customerPaymentsRecord.getIsActive());
        customerPaymentDao.setState(customerPaymentsRecord.getState());
        customerPaymentDao.setApiSource(customerPaymentsRecord.getApiSource());
        customerPaymentDao.setNotes(customerPaymentsRecord.getNotes());
        return customerPaymentDao;
    }

    @Override
    public CustomerPaymentDao update(CustomerPaymentDao customerPaymentDao) {
        CustomerPaymentsRecord customerPaymentsRecord = context.newRecord(CUSTOMER_PAYMENTS, customerPaymentDao);
        customerPaymentsRecord.update();


        customerPaymentDao.setCreatedBy(customerPaymentsRecord.getCreatedBy());
        customerPaymentDao.setUpdatedBy(customerPaymentsRecord.getUpdatedBy());
        customerPaymentDao.setCreatedOn(customerPaymentsRecord.getCreatedOn());
        customerPaymentDao.setUpdatedOn(customerPaymentsRecord.getUpdatedOn());
        customerPaymentDao.setIsActive(customerPaymentsRecord.getIsActive());
        customerPaymentDao.setState(customerPaymentsRecord.getState());
        customerPaymentDao.setApiSource(customerPaymentsRecord.getApiSource());
        customerPaymentDao.setNotes(customerPaymentsRecord.getNotes());
        return customerPaymentDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_PAYMENTS)
                .set(CUSTOMER_PAYMENTS.STATE, RecordState.DELETED.name())
                .where(CUSTOMER_PAYMENTS.ID.eq(id))
                .and(CUSTOMER_PAYMENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(CUSTOMER_PAYMENTS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public CustomerPaymentDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_PAYMENTS).where(CUSTOMER_PAYMENTS.UUID.eq(uuid))
                .and(CUSTOMER_PAYMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerPaymentDao.class);
    }

    @Override
    public CustomerPaymentDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_PAYMENTS).where(CUSTOMER_PAYMENTS.ID.eq(id))
                .and(CUSTOMER_PAYMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerPaymentDao.class);
    }

    @Override
    public List<CustomerPaymentDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_PAYMENTS).where(CUSTOMER_PAYMENTS.ID.in(ids)).fetchInto(CustomerPaymentDao.class);
    }

    @Override
    public List<CustomerPaymentDao> findAllActive() {
        return context.selectFrom(CUSTOMER_PAYMENTS).fetchInto(CustomerPaymentDao.class);
    }
}
