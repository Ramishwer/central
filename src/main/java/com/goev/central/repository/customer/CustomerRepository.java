package com.goev.central.repository.customer;

import com.goev.central.dao.customer.CustomerDao;

import java.util.List;

public interface CustomerRepository {
    CustomerDao save(CustomerDao customer);
    CustomerDao update(CustomerDao customer);
    void delete(Integer id);
    CustomerDao findByUUID(String uuid);
    CustomerDao findById(Integer id);
    List<CustomerDao> findAllByIds(List<Integer> ids);
    List<CustomerDao> findAll();

}
