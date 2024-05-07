package com.goev.central.repository.customer.wallet.impl;

import com.goev.central.dao.customer.wallet.CustomerWalletTransactionDao;
import com.goev.central.repository.customer.wallet.CustomerWalletTransactionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerWalletTransactionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerWalletTransactions.CUSTOMER_WALLET_TRANSACTIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerWalletTransactionRepositoryImpl implements CustomerWalletTransactionRepository {

    private final DSLContext context;

    @Override
    public CustomerWalletTransactionDao save(CustomerWalletTransactionDao customerWalletTransaction) {
        CustomerWalletTransactionsRecord customerWalletTransactionsRecord = context.newRecord(CUSTOMER_WALLET_TRANSACTIONS, customerWalletTransaction);
        customerWalletTransactionsRecord.store();
        customerWalletTransaction.setId(customerWalletTransactionsRecord.getId());
        customerWalletTransaction.setUuid(customerWalletTransaction.getUuid());
        return customerWalletTransaction;
    }

    @Override
    public CustomerWalletTransactionDao update(CustomerWalletTransactionDao customerWalletTransaction) {
        CustomerWalletTransactionsRecord customerWalletTransactionsRecord = context.newRecord(CUSTOMER_WALLET_TRANSACTIONS, customerWalletTransaction);
        customerWalletTransactionsRecord.update();
        return customerWalletTransaction;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_WALLET_TRANSACTIONS).set(CUSTOMER_WALLET_TRANSACTIONS.STATE, RecordState.DELETED.name()).where(CUSTOMER_WALLET_TRANSACTIONS.ID.eq(id)).execute();
    }

    @Override
    public CustomerWalletTransactionDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_WALLET_TRANSACTIONS).where(CUSTOMER_WALLET_TRANSACTIONS.UUID.eq(uuid)).fetchAnyInto(CustomerWalletTransactionDao.class);
    }

    @Override
    public CustomerWalletTransactionDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_WALLET_TRANSACTIONS).where(CUSTOMER_WALLET_TRANSACTIONS.ID.eq(id)).fetchAnyInto(CustomerWalletTransactionDao.class);
    }

    @Override
    public List<CustomerWalletTransactionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_WALLET_TRANSACTIONS).where(CUSTOMER_WALLET_TRANSACTIONS.ID.in(ids)).fetchInto(CustomerWalletTransactionDao.class);
    }

    @Override
    public List<CustomerWalletTransactionDao> findAll() {
        return context.selectFrom(CUSTOMER_WALLET_TRANSACTIONS).fetchInto(CustomerWalletTransactionDao.class);
    }
}
