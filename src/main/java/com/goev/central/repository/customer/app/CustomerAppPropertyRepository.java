package com.goev.central.repository.customer.app;

import com.goev.central.dao.customer.app.CustomerAppPropertyDao;

import java.util.List;

public interface CustomerAppPropertyRepository {
    CustomerAppPropertyDao save(CustomerAppPropertyDao partner);
    CustomerAppPropertyDao update(CustomerAppPropertyDao partner);
    void delete(Integer id);
    CustomerAppPropertyDao findByUUID(String uuid);
    CustomerAppPropertyDao findById(Integer id);
    List<CustomerAppPropertyDao> findAllByIds(List<Integer> ids);
    List<CustomerAppPropertyDao> findAll();
}