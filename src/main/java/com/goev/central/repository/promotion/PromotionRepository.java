package com.goev.central.repository.promotion;

import com.goev.central.dao.promotion.PromotionDao;

import java.util.List;

public interface PromotionRepository {
    PromotionDao save(PromotionDao partner);

    PromotionDao update(PromotionDao partner);

    void delete(Integer id);

    PromotionDao findByUUID(String uuid);

    PromotionDao findById(Integer id);

    List<PromotionDao> findAllByIds(List<Integer> ids);

    List<PromotionDao> findAllActive();
}