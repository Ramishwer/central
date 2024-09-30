package com.goev.central.repository.partner.incentive;

import com.goev.central.dao.partner.incentive.PartnerIncentiveMappingDao;

import java.util.List;

public interface PartnerIncentiveMappingRepository {
    PartnerIncentiveMappingDao save(PartnerIncentiveMappingDao partner);

    PartnerIncentiveMappingDao update(PartnerIncentiveMappingDao partner);

    void delete(Integer id);

    PartnerIncentiveMappingDao findByUUID(String uuid);

    PartnerIncentiveMappingDao findById(Integer id);

    List<PartnerIncentiveMappingDao> findAllByIds(List<Integer> ids);

    List<PartnerIncentiveMappingDao> findAllActive();

    List<PartnerIncentiveMappingDao> findAllByPartnerId(Integer partnerId);
}