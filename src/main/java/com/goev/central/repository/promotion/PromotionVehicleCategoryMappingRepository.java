package com.goev.central.repository.promotion;

import com.goev.central.dao.promotion.PromotionVehicleCategoryMappingDao;

import java.util.List;

public interface PromotionVehicleCategoryMappingRepository {
    PromotionVehicleCategoryMappingDao save(PromotionVehicleCategoryMappingDao partner);

    PromotionVehicleCategoryMappingDao update(PromotionVehicleCategoryMappingDao partner);

    void delete(Integer id);

    PromotionVehicleCategoryMappingDao findByUUID(String uuid);

    PromotionVehicleCategoryMappingDao findById(Integer id);

    List<PromotionVehicleCategoryMappingDao> findAllByIds(List<Integer> ids);

    List<PromotionVehicleCategoryMappingDao> findAllActive();
}