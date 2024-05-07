package com.goev.central.repository.partner.passbook;

import com.goev.central.dao.partner.passbook.PartnerPassbookDetailDao;

import java.util.List;

public interface PartnerPassbookDetailRepository {
    PartnerPassbookDetailDao save(PartnerPassbookDetailDao partner);
    PartnerPassbookDetailDao update(PartnerPassbookDetailDao partner);
    void delete(Integer id);
    PartnerPassbookDetailDao findByUUID(String uuid);
    PartnerPassbookDetailDao findById(Integer id);
    List<PartnerPassbookDetailDao> findAllByIds(List<Integer> ids);
    List<PartnerPassbookDetailDao> findAll();

    PartnerPassbookDetailDao findByPartnerId(Integer id);
}