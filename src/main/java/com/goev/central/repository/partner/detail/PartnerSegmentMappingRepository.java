package com.goev.central.repository.partner.detail;

import com.goev.central.dao.partner.detail.PartnerSegmentMappingDao;

import java.util.List;

public interface PartnerSegmentMappingRepository {
    PartnerSegmentMappingDao save(PartnerSegmentMappingDao partner);

    PartnerSegmentMappingDao update(PartnerSegmentMappingDao partner);

    void delete(Integer id);

    PartnerSegmentMappingDao findByUUID(String uuid);

    PartnerSegmentMappingDao findById(Integer id);

    List<PartnerSegmentMappingDao> findAllByIds(List<Integer> ids);

    List<PartnerSegmentMappingDao> findAllActive();

    List<PartnerSegmentMappingDao> findAllBySegmentId(Integer partnerSegmentId);
    List<PartnerSegmentMappingDao> findAllPartnerBySegmentId(Integer partnerSegmentId);

    List<PartnerSegmentMappingDao> findAllVehicleSegmentBySegmentId(Integer partnerSegmentId);
}