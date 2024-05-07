package com.goev.central.repository.partner.payout;

import com.goev.central.dao.partner.payout.PartnerPayoutTransactionDao;

import java.util.List;

public interface PartnerPayoutTransactionRepository {
    PartnerPayoutTransactionDao save(PartnerPayoutTransactionDao partner);
    PartnerPayoutTransactionDao update(PartnerPayoutTransactionDao partner);
    void delete(Integer id);
    PartnerPayoutTransactionDao findByUUID(String uuid);
    PartnerPayoutTransactionDao findById(Integer id);
    List<PartnerPayoutTransactionDao> findAllByIds(List<Integer> ids);
    List<PartnerPayoutTransactionDao> findAll();

    List<PartnerPayoutTransactionDao> findAllByPartnerPayoutId(Integer id);
}