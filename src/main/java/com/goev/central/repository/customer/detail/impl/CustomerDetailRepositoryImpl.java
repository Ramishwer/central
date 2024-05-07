package com.goev.central.repository.customer.detail.impl;

import com.goev.central.dao.customer.detail.CustomerDetailDao;
import com.goev.central.repository.customer.detail.CustomerDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerDetails.CUSTOMER_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerDetailRepositoryImpl implements CustomerDetailRepository {

    private final DSLContext context;

    @Override
    public CustomerDetailDao save(CustomerDetailDao customerDetail) {
        CustomerDetailsRecord customerDetailsRecord = context.newRecord(CUSTOMER_DETAILS, customerDetail);
        customerDetailsRecord.store();
        customerDetail.setId(customerDetailsRecord.getId());
        customerDetail.setUuid(customerDetail.getUuid());
        return customerDetail;
    }

    @Override
    public CustomerDetailDao update(CustomerDetailDao customerDetail) {
        CustomerDetailsRecord customerDetailsRecord = context.newRecord(CUSTOMER_DETAILS, customerDetail);
        customerDetailsRecord.update();
        return customerDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_DETAILS).set(CUSTOMER_DETAILS.STATE, RecordState.DELETED.name()).where(CUSTOMER_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public CustomerDetailDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_DETAILS).where(CUSTOMER_DETAILS.UUID.eq(uuid)).fetchAnyInto(CustomerDetailDao.class);
    }

    @Override
    public CustomerDetailDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_DETAILS).where(CUSTOMER_DETAILS.ID.eq(id)).fetchAnyInto(CustomerDetailDao.class);
    }

    @Override
    public List<CustomerDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_DETAILS).where(CUSTOMER_DETAILS.ID.in(ids)).fetchInto(CustomerDetailDao.class);
    }

    @Override
    public List<CustomerDetailDao> findAll() {
        return context.selectFrom(CUSTOMER_DETAILS).fetchInto(CustomerDetailDao.class);
    }
}
