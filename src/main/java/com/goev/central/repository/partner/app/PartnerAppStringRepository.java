package com.goev.central.repository.partner.app;

import com.goev.central.dao.partner.app.PartnerAppStringDao;

import java.util.List;

public interface PartnerAppStringRepository {
    PartnerAppStringDao save(PartnerAppStringDao partner);

    PartnerAppStringDao update(PartnerAppStringDao partner);

    void delete(Integer id);

    PartnerAppStringDao findByUUID(String uuid);

    PartnerAppStringDao findById(Integer id);

    List<PartnerAppStringDao> findAllByIds(List<Integer> ids);

    List<PartnerAppStringDao> findAllActive();
}