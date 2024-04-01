package com.goev.central.repository.customer.detail;

import com.goev.central.dao.customer.detail.CustomerSessionDao;

import java.util.List;

public interface CustomerSessionRepository {
    CustomerSessionDao save(CustomerSessionDao partner);
    CustomerSessionDao update(CustomerSessionDao partner);
    void delete(Integer id);
    CustomerSessionDao findByUUID(String uuid);
    CustomerSessionDao findById(Integer id);
    List<CustomerSessionDao> findAllByIds(List<Integer> ids);
    List<CustomerSessionDao> findAll();
}