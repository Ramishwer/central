package com.goev.central.repository.partner.payout;

import com.goev.central.dao.partner.payout.PartnerPayoutTransactionDao;
import org.joda.time.DateTime;

import java.util.List;

public interface PartnerPayoutTransactionRepository {
    PartnerPayoutTransactionDao save(PartnerPayoutTransactionDao partner);

    PartnerPayoutTransactionDao update(PartnerPayoutTransactionDao partner);

    void delete(Integer id);

    PartnerPayoutTransactionDao findByUUID(String uuid);

    PartnerPayoutTransactionDao findById(Integer id);

    List<PartnerPayoutTransactionDao> findAllByIds(List<Integer> ids);

    List<PartnerPayoutTransactionDao> findAllActive();

    List<PartnerPayoutTransactionDao> findAllByPartnerPayoutId(Integer id);

    PartnerPayoutTransactionDao findByPartnerPayoutIdAndDayAndDate(Integer partnerPayoutId, String day, String string);

}