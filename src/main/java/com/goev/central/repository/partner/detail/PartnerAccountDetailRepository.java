package com.goev.central.repository.partner.detail;

import com.goev.central.dao.partner.detail.PartnerAccountDetailDao;

import java.util.List;

public interface PartnerAccountDetailRepository {
    PartnerAccountDetailDao save(PartnerAccountDetailDao accountDetail);
    PartnerAccountDetailDao update(PartnerAccountDetailDao accountDetail);
    void delete(Integer id);
    PartnerAccountDetailDao findByUUID(String uuid);
    PartnerAccountDetailDao findById(Integer id);
    List<PartnerAccountDetailDao> findAllByIds(List<Integer> ids);
    List<PartnerAccountDetailDao> findAll();
    List<PartnerAccountDetailDao> findAllByPartnerId(Integer partnerId);
}