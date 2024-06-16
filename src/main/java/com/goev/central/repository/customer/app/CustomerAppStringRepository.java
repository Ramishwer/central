package com.goev.central.repository.customer.app;

import com.goev.central.dao.customer.app.CustomerAppStringDao;

import java.util.List;

public interface CustomerAppStringRepository {
    CustomerAppStringDao save(CustomerAppStringDao partner);

    CustomerAppStringDao update(CustomerAppStringDao partner);

    void delete(Integer id);

    CustomerAppStringDao findByUUID(String uuid);

    CustomerAppStringDao findById(Integer id);

    List<CustomerAppStringDao> findAllByIds(List<Integer> ids);

    List<CustomerAppStringDao> findAllActive();
}