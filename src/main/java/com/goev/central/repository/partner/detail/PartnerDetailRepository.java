package com.goev.central.repository.partner.detail;

import com.goev.central.dao.partner.detail.PartnerDetailDao;

import java.util.List;

public interface PartnerDetailRepository {
    PartnerDetailDao save(PartnerDetailDao partnerDetail);

    PartnerDetailDao update(PartnerDetailDao partnerDetail);

    void delete(Integer id);

    PartnerDetailDao findByUUID(String uuid);

    PartnerDetailDao findById(Integer id);

    List<PartnerDetailDao> findAllByIds(List<Integer> ids);

    PartnerDetailDao findByAadhaarCardNumber(String aadhaarCardNumber);
}