package com.goev.central.repository.partner.detail;

import com.goev.central.dao.partner.detail.PartnerDao;

import java.util.List;

public interface PartnerRepository {
    PartnerDao save(PartnerDao partner);
    PartnerDao update(PartnerDao partner);
    void delete(Integer id);
    PartnerDao findByUUID(String uuid);
    PartnerDao findById(Integer id);
    List<PartnerDao> findAllByIds(List<Integer> ids);
    List<PartnerDao> findAll();

    PartnerDao findByPhoneNumber(String phoneNumber);

    List<PartnerDao> findAllByOnboardingStatus(String onboardingStatus);
}