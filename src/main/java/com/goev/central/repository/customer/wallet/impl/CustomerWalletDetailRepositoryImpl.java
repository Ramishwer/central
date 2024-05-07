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
        customerWalletDetail.setUuid(customerWalletDetail.getUuid());
        return customerWalletDetail;
    }

    @Override
    public CustomerWalletDetailDao update(CustomerWalletDetailDao customerWalletDetail) {
        CustomerWalletDetailsRecord customerWalletDetailsRecord = context.newRecord(CUSTOMER_WALLET_DETAILS, customerWalletDetail);
        customerWalletDetailsRecord.update();
        return customerWalletDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_WALLET_DETAILS).set(CUSTOMER_WALLET_DETAILS.STATE, RecordState.DELETED.name()).where(CUSTOMER_WALLET_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public CustomerWalletDetailDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_WALLET_DETAILS).where(CUSTOMER_WALLET_DETAILS.UUID.eq(uuid)).fetchAnyInto(CustomerWalletDetailDao.class);
    }

    @Override
    public CustomerWalletDetailDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_WALLET_DETAILS).where(CUSTOMER_WALLET_DETAILS.ID.eq(id)).fetchAnyInto(CustomerWalletDetailDao.class);
    }

    @Override
    public List<CustomerWalletDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_WALLET_DETAILS).where(CUSTOMER_WALLET_DETAILS.ID.in(ids)).fetchInto(CustomerWalletDetailDao.class);
    }

    @Override
    public List<CustomerWalletDetailDao> findAll() {
        return context.selectFrom(CUSTOMER_WALLET_DETAILS).fetchInto(CustomerWalletDetailDao.class);
    }

    @Override
    public CustomerWalletDetailDao findByCustomerId(Integer id) {
       return context.selectFrom(CUSTOMER_WALLET_DETAILS).where(CUSTOMER_WALLET_DETAILS.CUSTOMER_ID.eq(id)).fetchOneInto(CustomerWalletDetailDao.class);
    }
}
