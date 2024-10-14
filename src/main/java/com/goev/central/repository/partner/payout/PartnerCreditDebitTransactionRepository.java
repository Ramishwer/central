package com.goev.central.repository.partner.payout;

import com.goev.central.dao.partner.payout.PartnerCreditDebitTransactionDao;
import org.joda.time.DateTime;

import java.util.List;

public interface PartnerCreditDebitTransactionRepository {
    PartnerCreditDebitTransactionDao save(PartnerCreditDebitTransactionDao partner);

    PartnerCreditDebitTransactionDao update(PartnerCreditDebitTransactionDao partner);

    void delete(Integer id);

    PartnerCreditDebitTransactionDao findByUUID(String uuid);

    PartnerCreditDebitTransactionDao findById(Integer id);

    List<PartnerCreditDebitTransactionDao> findAllByIds(List<Integer> ids);

    List<PartnerCreditDebitTransactionDao> findAllActive();

    List<PartnerCreditDebitTransactionDao> findAllByPartnerPayoutIdAndPartnerId(Integer payoutId,Integer partnerId);

    List<PartnerCreditDebitTransactionDao> findAllByPartnerPayoutIdAndPartnerIdAndTransactionTimeBetween(Integer payoutId, Integer partnerId, DateTime start,DateTime end);



}