package com.goev.central.repository.promotion;

import com.goev.central.dao.promotion.PromotionDetailDao;

import java.util.List;

public interface PromotionDetailRepository {
    PromotionDetailDao save(PromotionDetailDao partner);

    PromotionDetailDao update(PromotionDetailDao partner);

    void delete(Integer id);

    PromotionDetailDao findByUUID(String uuid);

    PromotionDetailDao findById(Integer id);

    List<PromotionDetailDao> findAllByIds(List<Integer> ids);

    List<PromotionDetailDao> findAllActive();
}