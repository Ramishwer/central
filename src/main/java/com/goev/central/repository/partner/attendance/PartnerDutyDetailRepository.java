package com.goev.central.repository.partner.attendance;

import com.goev.central.dao.partner.attendance.PartnerDutyDetailDao;

import java.util.List;

public interface PartnerDutyDetailRepository {
    PartnerDutyDetailDao save(PartnerDutyDetailDao partner);
    PartnerDutyDetailDao update(PartnerDutyDetailDao partner);
    void delete(Integer id);
    PartnerDutyDetailDao findByUUID(String uuid);
    PartnerDutyDetailDao findById(Integer id);
    List<PartnerDutyDetailDao> findAllByIds(List<Integer> ids);
    List<PartnerDutyDetailDao> findAll();
}