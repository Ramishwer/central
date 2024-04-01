package com.goev.central.repository.promotion;

import com.goev.central.dao.promotion.PromotionRegionMappingDao;

import java.util.List;

public interface PromotionRegionMappingRepository {
    PromotionRegionMappingDao save(PromotionRegionMappingDao partner);
    PromotionRegionMappingDao update(PromotionRegionMappingDao partner);
    void delete(Integer id);
    PromotionRegionMappingDao findByUUID(String uuid);
    PromotionRegionMappingDao findById(Integer id);
    List<PromotionRegionMappingDao> findAllByIds(List<Integer> ids);
    List<PromotionRegionMappingDao> findAll();
}