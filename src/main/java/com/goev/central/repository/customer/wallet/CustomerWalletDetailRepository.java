package com.goev.central.repository.customer.wallet;

import com.goev.central.dao.customer.wallet.CustomerWalletDetailDao;

import java.util.List;

public interface CustomerWalletDetailRepository {
    CustomerWalletDetailDao save(CustomerWalletDetailDao partner);
    CustomerWalletDetailDao update(CustomerWalletDetailDao partner);
    void delete(Integer id);
    CustomerWalletDetailDao findByUUID(String uuid);
    CustomerWalletDetailDao findById(Integer id);
    List<CustomerWalletDetailDao> findAllByIds(List<Integer> ids);
    List<CustomerWalletDetailDao> findAll();

    CustomerWalletDetailDao findByCustomerId(Integer id);
}