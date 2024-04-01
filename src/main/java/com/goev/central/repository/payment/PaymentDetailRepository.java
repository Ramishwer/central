package com.goev.central.repository.payment;

import com.goev.central.dao.payment.PaymentDetailDao;

import java.util.List;

public interface PaymentDetailRepository {
    PaymentDetailDao save(PaymentDetailDao partner);
    PaymentDetailDao update(PaymentDetailDao partner);
    void delete(Integer id);
    PaymentDetailDao findByUUID(String uuid);
    PaymentDetailDao findById(Integer id);
    List<PaymentDetailDao> findAllByIds(List<Integer> ids);
    List<PaymentDetailDao> findAll();
}