package com.goev.central.repository.pricing;

import com.goev.central.dao.pricing.PricingModelDao;

import java.util.List;

public interface PricingModelRepository {
    PricingModelDao save(PricingModelDao pricingModel);
    PricingModelDao update(PricingModelDao pricingModel);
    void delete(Integer id);
    PricingModelDao findByUUID(String uuid);
    PricingModelDao findById(Integer id);
    List<PricingModelDao> findAllByIds(List<Integer> ids);
    List<PricingModelDao> findAll();
}