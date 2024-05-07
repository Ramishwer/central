package com.goev.central.repository.partner.payout;

import com.goev.central.dao.partner.payout.PartnerPayoutDao;

import java.util.List;

public interface PartnerPayoutRepository {
    PartnerPayoutDao save(PartnerPayoutDao partner);
    PartnerPayoutDao update(PartnerPayoutDao partner);
    void delete(Integer id);
    PartnerPayoutDao findByUUID(String uuid);
    PartnerPayoutDao findById(Integer id);
    List<PartnerPayoutDao> findAllByIds(List<Integer> ids);
    List<PartnerPayoutDao> findAll();

    List<PartnerPayoutDao> findAllByPartnerId(Integer id);
}