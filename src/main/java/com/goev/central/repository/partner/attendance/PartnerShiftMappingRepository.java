package com.goev.central.repository.partner.attendance;

import com.goev.central.dao.partner.attendance.PartnerShiftMappingDao;

import java.util.List;

public interface PartnerShiftMappingRepository {
    PartnerShiftMappingDao save(PartnerShiftMappingDao partner);
    PartnerShiftMappingDao update(PartnerShiftMappingDao partner);
    void delete(Integer id);
    PartnerShiftMappingDao findByUUID(String uuid);
    PartnerShiftMappingDao findById(Integer id);
    List<PartnerShiftMappingDao> findAllByIds(List<Integer> ids);
    List<PartnerShiftMappingDao> findAll();
}