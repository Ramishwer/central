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
        customerDetail.setCreatedBy(customerDetail.getCreatedBy());
        customerDetail.setUpdatedBy(customerDetail.getUpdatedBy());
        customerDetail.setCreatedOn(customerDetail.getCreatedOn());
        customerDetail.setUpdatedOn(customerDetail.getUpdatedOn());
        customerDetail.setIsActive(customerDetail.getIsActive());
        customerDetail.setState(customerDetail.getState());
        customerDetail.setApiSource(customerDetail.getApiSource());
        customerDetail.setNotes(customerDetail.getNotes());
        return customerDetail;
    }

    @Override
    public CustomerDetailDao update(CustomerDetailDao customerDetail) {
        CustomerDetailsRecord customerDetailsRecord = context.newRecord(CUSTOMER_DETAILS, customerDetail);
        customerDetailsRecord.update();


        customerDetail.setCreatedBy(customerDetailsRecord.getCreatedBy());
        customerDetail.setUpdatedBy(customerDetailsRecord.getUpdatedBy());
        customerDetail.setCreatedOn(customerDetailsRecord.getCreatedOn());
        customerDetail.setUpdatedOn(customerDetailsRecord.getUpdatedOn());
        customerDetail.setIsActive(customerDetailsRecord.getIsActive());
        customerDetail.setState(customerDetailsRecord.getState());
        customerDetail.setApiSource(customerDetailsRecord.getApiSource());
        customerDetail.setNotes(customerDetailsRecord.getNotes());
        return customerDetail;
    }

    @Override
    public void delete(Integer id) {
     context.update(CUSTOMER_DETAILS)
     .set(CUSTOMER_DETAILS.STATE,RecordState.DELETED.name())
     .where(CUSTOMER_DETAILS.ID.eq(id))
     .and(CUSTOMER_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
     .and(CUSTOMER_DETAILS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public CustomerDetailDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_DETAILS).where(CUSTOMER_DETAILS.UUID.eq(uuid))
                .and(CUSTOMER_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerDetailDao.class);
    }

    @Override
    public CustomerDetailDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_DETAILS).where(CUSTOMER_DETAILS.ID.eq(id))
                .and(CUSTOMER_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerDetailDao.class);
    }

    @Override
    public List<CustomerDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_DETAILS).where(CUSTOMER_DETAILS.ID.in(ids)).fetchInto(CustomerDetailDao.class);
    }

    @Override
    public List<CustomerDetailDao> findAllActive() {
        return context.selectFrom(CUSTOMER_DETAILS).fetchInto(CustomerDetailDao.class);
    }
}
