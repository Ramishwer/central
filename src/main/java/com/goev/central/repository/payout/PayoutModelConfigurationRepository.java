package com.goev.central.repository.payout;

import com.goev.central.dao.payout.PayoutModelConfigurationDao;

import java.util.List;

public interface PayoutModelConfigurationRepository {
    PayoutModelConfigurationDao save(PayoutModelConfigurationDao partner);
    PayoutModelConfigurationDao update(PayoutModelConfigurationDao partner);
    void delete(Integer id);
    PayoutModelConfigurationDao findByUUID(String uuid);
    PayoutModelConfigurationDao findById(Integer id);
    List<PayoutModelConfigurationDao> findAllByIds(List<Integer> ids);
    List<PayoutModelConfigurationDao> findAll();
}