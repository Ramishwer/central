package com.goev.central.repository.partner.payout;

import com.goev.central.dao.partner.payout.PartnerPayoutModelDao;

import java.util.List;

public interface PartnerPayoutModelRepository {
    PartnerPayoutModelDao save(PartnerPayoutModelDao partner);
    PartnerPayoutModelDao update(PartnerPayoutModelDao partner);
    void delete(Integer id);
    PartnerPayoutModelDao findByUUID(String uuid);
    PartnerPayoutModelDao findById(Integer id);
    List<PartnerPayoutModelDao> findAllByIds(List<Integer> ids);
    List<PartnerPayoutModelDao> findAll();
}