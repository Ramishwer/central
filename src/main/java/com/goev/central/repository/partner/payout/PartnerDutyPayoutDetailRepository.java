package com.goev.central.repository.partner.payout;

import com.goev.central.dao.partner.payout.PartnerDutyPayoutDetailDao;

import java.util.List;

public interface PartnerDutyPayoutDetailRepository {
    PartnerDutyPayoutDetailDao save(PartnerDutyPayoutDetailDao partner);

    PartnerDutyPayoutDetailDao update(PartnerDutyPayoutDetailDao partner);

    void delete(Integer id);

    PartnerDutyPayoutDetailDao findByUUID(String uuid);

    PartnerDutyPayoutDetailDao findById(Integer id);

    List<PartnerDutyPayoutDetailDao> findAllByIds(List<Integer> ids);

    List<PartnerDutyPayoutDetailDao> findAllActive();
}