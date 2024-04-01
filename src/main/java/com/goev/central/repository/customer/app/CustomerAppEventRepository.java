package com.goev.central.repository.customer.app;

import com.goev.central.dao.customer.app.CustomerAppEventDao;

import java.util.List;

public interface CustomerAppEventRepository {
    CustomerAppEventDao save(CustomerAppEventDao event);
    CustomerAppEventDao update(CustomerAppEventDao event);
    void delete(Integer id);
    CustomerAppEventDao findByUUID(String uuid);
    CustomerAppEventDao findById(Integer id);
    List<CustomerAppEventDao> findAllByIds(List<Integer> ids);
    List<CustomerAppEventDao> findAll();
}