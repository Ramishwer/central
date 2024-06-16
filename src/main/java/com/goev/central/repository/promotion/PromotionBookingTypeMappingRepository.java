package com.goev.central.repository.promotion;

import com.goev.central.dao.promotion.PromotionBookingTypeMappingDao;

import java.util.List;

public interface PromotionBookingTypeMappingRepository {
    PromotionBookingTypeMappingDao save(PromotionBookingTypeMappingDao partner);

    PromotionBookingTypeMappingDao update(PromotionBookingTypeMappingDao partner);

    void delete(Integer id);

    PromotionBookingTypeMappingDao findByUUID(String uuid);

    PromotionBookingTypeMappingDao findById(Integer id);

    List<PromotionBookingTypeMappingDao> findAllByIds(List<Integer> ids);

    List<PromotionBookingTypeMappingDao> findAllActive();
}