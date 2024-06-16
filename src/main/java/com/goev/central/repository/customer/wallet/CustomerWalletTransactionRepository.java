package com.goev.central.repository.customer.wallet;

import com.goev.central.dao.customer.wallet.CustomerWalletTransactionDao;

import java.util.List;

public interface CustomerWalletTransactionRepository {
    CustomerWalletTransactionDao save(CustomerWalletTransactionDao partner);

    CustomerWalletTransactionDao update(CustomerWalletTransactionDao partner);

    void delete(Integer id);

    CustomerWalletTransactionDao findByUUID(String uuid);

    CustomerWalletTransactionDao findById(Integer id);

    List<CustomerWalletTransactionDao> findAllByIds(List<Integer> ids);

    List<CustomerWalletTransactionDao> findAllActive();
}