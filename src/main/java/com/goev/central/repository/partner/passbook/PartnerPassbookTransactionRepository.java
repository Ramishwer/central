package com.goev.central.repository.partner.passbook;

import com.goev.central.dao.partner.passbook.PartnerPassbookTransactionDao;

import java.util.List;

public interface PartnerPassbookTransactionRepository {
    PartnerPassbookTransactionDao save(PartnerPassbookTransactionDao partner);

    PartnerPassbookTransactionDao update(PartnerPassbookTransactionDao partner);

    void delete(Integer id);

    PartnerPassbookTransactionDao findByUUID(String uuid);

    PartnerPassbookTransactionDao findById(Integer id);

    List<PartnerPassbookTransactionDao> findAllByIds(List<Integer> ids);

    List<PartnerPassbookTransactionDao> findAllActive();
}
