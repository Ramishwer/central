package com.goev.central.repository.partner.payout;

import com.goev.central.dao.partner.payout.PartnerPayoutMappingDao;

import java.util.List;

public interface PartnerPayoutMappingRepository {
    PartnerPayoutMappingDao save(PartnerPayoutMappingDao partner);

    PartnerPayoutMappingDao update(PartnerPayoutMappingDao partner);

    void delete(Integer id);

    PartnerPayoutMappingDao findByUUID(String uuid);

    PartnerPayoutMappingDao findById(Integer id);

    List<PartnerPayoutMappingDao> findAllByIds(List<Integer> ids);

    List<PartnerPayoutMappingDao> findAllActive();

    List<PartnerPayoutMappingDao> findAllByPartnerId(Integer partnerId);
}