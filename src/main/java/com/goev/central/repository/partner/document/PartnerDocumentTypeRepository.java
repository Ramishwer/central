package com.goev.central.repository.partner.document;

import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;

import java.util.List;

public interface PartnerDocumentTypeRepository {
    PartnerDocumentTypeDao save(PartnerDocumentTypeDao partner);
    PartnerDocumentTypeDao update(PartnerDocumentTypeDao partner);
    void delete(Integer id);
    PartnerDocumentTypeDao findByUUID(String uuid);
    PartnerDocumentTypeDao findById(Integer id);
    List<PartnerDocumentTypeDao> findAllByIds(List<Integer> ids);
    List<PartnerDocumentTypeDao> findAll();
}