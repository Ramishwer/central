package com.goev.central.repository.payout;

import com.goev.central.dao.payout.PayoutModelDao;

import java.util.List;

public interface PayoutModelRepository {
    PayoutModelDao save(PayoutModelDao partner);

    PayoutModelDao update(PayoutModelDao partner);

    void delete(Integer id);

    PayoutModelDao findByUUID(String uuid);

    PayoutModelDao findById(Integer id);

    List<PayoutModelDao> findAllByIds(List<Integer> ids);

    List<PayoutModelDao> findAllActive();
}