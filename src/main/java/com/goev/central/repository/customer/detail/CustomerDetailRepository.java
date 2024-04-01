package com.goev.central.repository.customer.detail;

import com.goev.central.dao.customer.detail.CustomerDetailDao;

import java.util.List;

public interface CustomerDetailRepository {
    CustomerDetailDao save(CustomerDetailDao partner);
    CustomerDetailDao update(CustomerDetailDao partner);
    void delete(Integer id);
    CustomerDetailDao findByUUID(String uuid);
    CustomerDetailDao findById(Integer id);
    List<CustomerDetailDao> findAllByIds(List<Integer> ids);
    List<CustomerDetailDao> findAll();
}