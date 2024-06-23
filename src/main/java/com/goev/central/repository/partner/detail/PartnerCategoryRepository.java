package com.goev.central.repository.partner.detail;

import com.goev.central.dao.partner.detail.PartnerCategoryDao;

import java.util.List;

public interface PartnerCategoryRepository {
    PartnerCategoryDao save(PartnerCategoryDao category);

    PartnerCategoryDao update(PartnerCategoryDao category);

    void delete(Integer id);

    PartnerCategoryDao findByUUID(String uuid);

    PartnerCategoryDao findById(Integer id);

    List<PartnerCategoryDao> findAllByIds(List<Integer> ids);

    List<PartnerCategoryDao> findAllActive();
}