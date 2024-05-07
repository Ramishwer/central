package com.goev.central.repository.payout;

import com.goev.central.dao.payout.PayoutElementDao;

import java.util.List;

public interface PayoutElementRepository {
    PayoutElementDao save(PayoutElementDao partner);
    PayoutElementDao update(PayoutElementDao partner);
    void delete(Integer id);
    PayoutElementDao findByUUID(String uuid);
    PayoutElementDao findById(Integer id);
    List<PayoutElementDao> findAllByIds(List<Integer> ids);
    List<PayoutElementDao> findAll();
}