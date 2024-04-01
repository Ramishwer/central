package com.goev.central.repository.customer.detail;

import com.goev.central.dao.customer.detail.CustomerDeviceDao;

import java.util.List;

public interface CustomerDeviceRepository {
    CustomerDeviceDao save(CustomerDeviceDao partner);
    CustomerDeviceDao update(CustomerDeviceDao partner);
    void delete(Integer id);
    CustomerDeviceDao findByUUID(String uuid);
    CustomerDeviceDao findById(Integer id);
    List<CustomerDeviceDao> findAllByIds(List<Integer> ids);
    List<CustomerDeviceDao> findAll();
}