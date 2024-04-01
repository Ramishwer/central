package com.goev.central.repository.partner.payout;

import com.goev.central.dao.partner.payout.PartnerBookingPayoutDetailDao;

import java.util.List;

public interface PartnerBookingPayoutDetailRepository {
    PartnerBookingPayoutDetailDao save(PartnerBookingPayoutDetailDao partner);
    PartnerBookingPayoutDetailDao update(PartnerBookingPayoutDetailDao partner);
    void delete(Integer id);
    PartnerBookingPayoutDetailDao findByUUID(String uuid);
    PartnerBookingPayoutDetailDao findById(Integer id);
    List<PartnerBookingPayoutDetailDao> findAllByIds(List<Integer> ids);
    List<PartnerBookingPayoutDetailDao> findAll();
}