package com.goev.central.repository.partner.payout;

import com.goev.central.dao.partner.payout.PartnerPayoutElementDao;

import java.util.List;

public interface PartnerPayoutElementRepository {
    PartnerPayoutElementDao save(PartnerPayoutElementDao partner);
    PartnerPayoutElementDao update(PartnerPayoutElementDao partner);
    void delete(Integer id);
    PartnerPayoutElementDao findByUUID(String uuid);
    PartnerPayoutElementDao findById(Integer id);
    List<PartnerPayoutElementDao> findAllByIds(List<Integer> ids);
    List<PartnerPayoutElementDao> findAll();
}