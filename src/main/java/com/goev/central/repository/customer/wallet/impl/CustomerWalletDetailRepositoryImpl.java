package com.goev.central.repository.customer.wallet.impl;

import com.goev.central.dao.customer.wallet.CustomerWalletDetailDao;
import com.goev.central.repository.customer.wallet.CustomerWalletDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerWalletDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerWalletDetails.CUSTOMER_WALLET_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerWalletDetailRepositoryImpl implements CustomerWalletDetailRepository {

    private final DSLContext context;

    @Override
    public CustomerWalletDetailDao save(CustomerWalletDetailDao customerWalletDetail) {
        CustomerWalletDetailsRecord customerWalletDetailsRecord = context.newRecord(CUSTOMER_WALLET_DETAILS, customerWalletDetail);
        customerWalletDetailsRecord.store();
        customerWalletDetail.setId(customerWalletDetailsRecord.getId());
        customerWalletDetail.setUuid(customerWalletDetailsRecord.getUuid());
        customerWalletDetail.setCreatedBy(customerWalletDetailsRecord.getCreatedBy());
        customerWalletDetail.setUpdatedBy(customerWalletDetailsRecord.getUpdatedBy());
        customerWalletDetail.setCreatedOn(customerWalletDetailsRecord.getCreatedOn());
        customerWalletDetail.setUpdatedOn(customerWalletDetailsRecord.getUpdatedOn());
        customerWalletDetail.setIsActive(customerWalletDetailsRecord.getIsActive());
        customerWalletDetail.setState(customerWalletDetailsRecord.getState());
        customerWalletDetail.setApiSource(customerWalletDetailsRecord.getApiSource());
        customerWalletDetail.setNotes(customerWalletDetailsRecord.getNotes());
        return customerWalletDetail;
    }

    @Override
    public CustomerWalletDetailDao update(CustomerWalletDetailDao customerWalletDetail) {
        CustomerWalletDetailsRecord customerWalletDetailsRecord = context.newRecord(CUSTOMER_WALLET_DETAILS, customerWalletDetail);
        customerWalletDetailsRecord.update();


        customerWalletDetail.setCreatedBy(customerWalletDetailsRecord.getCreatedBy());
        customerWalletDetail.setUpdatedBy(customerWalletDetailsRecord.getUpdatedBy());
        customerWalletDetail.setCreatedOn(customerWalletDetailsRecord.getCreatedOn());
        customerWalletDetail.setUpdatedOn(customerWalletDetailsRecord.getUpdatedOn());
        customerWalletDetail.setIsActive(customerWalletDetailsRecord.getIsActive());
        customerWalletDetail.setState(customerWalletDetailsRecord.getState());
        customerWalletDetail.setApiSource(customerWalletDetailsRecord.getApiSource());
        customerWalletDetail.setNotes(customerWalletDetailsRecord.getNotes());
        return customerWalletDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_WALLET_DETAILS)
                .set(CUSTOMER_WALLET_DETAILS.STATE, RecordState.DELETED.name())
                .where(CUSTOMER_WALLET_DETAILS.ID.eq(id))
                .and(CUSTOMER_WALLET_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(CUSTOMER_WALLET_DETAILS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public CustomerWalletDetailDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_WALLET_DETAILS).where(CUSTOMER_WALLET_DETAILS.UUID.eq(uuid))
                .and(CUSTOMER_WALLET_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerWalletDetailDao.class);
    }

    @Override
    public CustomerWalletDetailDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_WALLET_DETAILS).where(CUSTOMER_WALLET_DETAILS.ID.eq(id))
                .and(CUSTOMER_WALLET_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerWalletDetailDao.class);
    }

    @Override
    public List<CustomerWalletDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_WALLET_DETAILS).where(CUSTOMER_WALLET_DETAILS.ID.in(ids)).fetchInto(CustomerWalletDetailDao.class);
    }

    @Override
    public List<CustomerWalletDetailDao> findAllActive() {
        return context.selectFrom(CUSTOMER_WALLET_DETAILS).fetchInto(CustomerWalletDetailDao.class);
    }

    @Override
    public CustomerWalletDetailDao findByCustomerId(Integer id) {
        return context.selectFrom(CUSTOMER_WALLET_DETAILS).where(CUSTOMER_WALLET_DETAILS.CUSTOMER_ID.eq(id)).fetchOneInto(CustomerWalletDetailDao.class);
    }
}
