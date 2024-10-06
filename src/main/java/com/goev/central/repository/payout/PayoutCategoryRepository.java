package com.goev.central.repository.payout;

import com.goev.central.dao.payout.PayoutCategoryDao;

import java.util.List;

public interface PayoutCategoryRepository {
    PayoutCategoryDao save(PayoutCategoryDao partner);

    PayoutCategoryDao update(PayoutCategoryDao partner);

    void delete(Integer id);

    PayoutCategoryDao findByUUID(String uuid);

    PayoutCategoryDao findById(Integer id);

    List<PayoutCategoryDao> findAllByIds(List<Integer> ids);

    List<PayoutCategoryDao> findAllActive();

}