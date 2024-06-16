package com.goev.central.repository.customer.promotion;

import com.goev.central.dao.customer.promotion.CustomerPromotionDao;

import java.util.List;

public interface CustomerPromotionRepository {
    CustomerPromotionDao save(CustomerPromotionDao partner);

    CustomerPromotionDao update(CustomerPromotionDao partner);

    void delete(Integer id);

    CustomerPromotionDao findByUUID(String uuid);

    CustomerPromotionDao findById(Integer id);

    List<CustomerPromotionDao> findAllByIds(List<Integer> ids);

    List<CustomerPromotionDao> findAllActive();
}