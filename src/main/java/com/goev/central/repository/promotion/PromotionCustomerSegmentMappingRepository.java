package com.goev.central.repository.promotion;

import com.goev.central.dao.promotion.PromotionCustomerSegmentMappingDao;

import java.util.List;

public interface PromotionCustomerSegmentMappingRepository {
    PromotionCustomerSegmentMappingDao save(PromotionCustomerSegmentMappingDao partner);

    PromotionCustomerSegmentMappingDao update(PromotionCustomerSegmentMappingDao partner);

    void delete(Integer id);

    PromotionCustomerSegmentMappingDao findByUUID(String uuid);

    PromotionCustomerSegmentMappingDao findById(Integer id);

    List<PromotionCustomerSegmentMappingDao> findAllByIds(List<Integer> ids);

    List<PromotionCustomerSegmentMappingDao> findAllActive();
}