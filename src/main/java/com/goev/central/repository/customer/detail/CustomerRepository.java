package com.goev.central.repository.customer.detail;

import com.goev.central.dao.customer.detail.CustomerDao;

import java.util.List;

public interface CustomerRepository {
    CustomerDao save(CustomerDao partner);

    CustomerDao update(CustomerDao partner);

    void delete(Integer id);

    CustomerDao findByUUID(String uuid);

    CustomerDao findById(Integer id);

    List<CustomerDao> findAllByIds(List<Integer> ids);

    List<CustomerDao> findAllActive(String onboardingStatus);

    CustomerDao findByPhoneNumber(String phoneNumber);
}