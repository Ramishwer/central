package com.goev.central.repository.partner.detail;

import com.goev.central.dao.partner.detail.PartnerSessionDao;

import java.util.List;

public interface PartnerSessionRepository {
    PartnerSessionDao save(PartnerSessionDao session);
    PartnerSessionDao update(PartnerSessionDao session);
    void delete(Integer id);
    PartnerSessionDao findByUUID(String uuid);
    PartnerSessionDao findById(Integer id);
    List<PartnerSessionDao> findAllByIds(List<Integer> ids);
    List<PartnerSessionDao> findAll();
}