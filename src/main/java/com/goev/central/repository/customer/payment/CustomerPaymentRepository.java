package com.goev.central.repository.customer.payment;

import com.goev.central.dao.customer.payment.CustomerPaymentDao;

import java.util.List;

public interface CustomerPaymentRepository {
    CustomerPaymentDao save(CustomerPaymentDao payment);

    CustomerPaymentDao update(CustomerPaymentDao payment);

    void delete(Integer id);

    CustomerPaymentDao findByUUID(String uuid);

    CustomerPaymentDao findById(Integer id);

    List<CustomerPaymentDao> findAllByIds(List<Integer> ids);

    List<CustomerPaymentDao> findAllActive();
}