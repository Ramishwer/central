package com.goev.central.repository.partner.detail;

import com.goev.central.dao.partner.detail.PartnerDeviceDao;

import java.util.List;

public interface PartnerDeviceRepository {
    PartnerDeviceDao save(PartnerDeviceDao device);

    PartnerDeviceDao update(PartnerDeviceDao device);

    void delete(Integer id);

    PartnerDeviceDao findByUUID(String uuid);

    PartnerDeviceDao findById(Integer id);

    List<PartnerDeviceDao> findAllByIds(List<Integer> ids);

    List<PartnerDeviceDao> findAllActive();
}