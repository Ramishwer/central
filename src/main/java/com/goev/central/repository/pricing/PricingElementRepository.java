package com.goev.central.repository.pricing;

import com.goev.central.dao.pricing.PricingElementDao;

import java.util.List;

public interface PricingElementRepository {
    PricingElementDao save(PricingElementDao pricingElement);
    PricingElementDao update(PricingElementDao pricingElement);
    void delete(Integer id);
    PricingElementDao findByUUID(String uuid);
    PricingElementDao findById(Integer id);
    List<PricingElementDao> findAllByIds(List<Integer> ids);
    List<PricingElementDao> findAll();
}