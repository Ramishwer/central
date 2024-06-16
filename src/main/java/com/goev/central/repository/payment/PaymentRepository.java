package com.goev.central.repository.payment;

import com.goev.central.dao.payment.PaymentDao;

import java.util.List;

public interface PaymentRepository {
    PaymentDao save(PaymentDao partner);

    PaymentDao update(PaymentDao partner);

    void delete(Integer id);

    PaymentDao findByUUID(String uuid);

    PaymentDao findById(Integer id);

    List<PaymentDao> findAllByIds(List<Integer> ids);

    List<PaymentDao> findAllActive();
}