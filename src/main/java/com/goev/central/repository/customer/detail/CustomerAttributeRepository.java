package com.goev.central.repository.customer.detail;

import com.goev.central.dao.customer.detail.CustomerAttributeDao;

import java.util.List;

public interface CustomerAttributeRepository {
    CustomerAttributeDao save(CustomerAttributeDao partner);

    CustomerAttributeDao update(CustomerAttributeDao partner);

    void delete(Integer id);

    CustomerAttributeDao findByUUID(String uuid);

    CustomerAttributeDao findById(Integer id);

    List<CustomerAttributeDao> findAllByIds(List<Integer> ids);

    List<CustomerAttributeDao> findAllActive();
}