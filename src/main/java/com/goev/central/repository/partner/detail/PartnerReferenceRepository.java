package com.goev.central.repository.partner.detail;

import com.goev.central.dao.partner.detail.PartnerReferenceDao;

import java.util.List;

public interface PartnerReferenceRepository {
    PartnerReferenceDao save(PartnerReferenceDao reference);
    PartnerReferenceDao update(PartnerReferenceDao reference);
    void delete(Integer id);
    PartnerReferenceDao findByUUID(String uuid);
    PartnerReferenceDao findById(Integer id);
    List<PartnerReferenceDao> findAllByIds(List<Integer> ids);
    List<PartnerReferenceDao> findAll();
    List<PartnerReferenceDao> findAllByPartnerId(Integer partnerId);
}


