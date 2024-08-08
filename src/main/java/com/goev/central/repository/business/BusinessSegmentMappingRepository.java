package com.goev.central.repository.business;

import com.goev.central.dao.business.BusinessSegmentMappingDao;

import java.util.List;

public interface BusinessSegmentMappingRepository {
    BusinessSegmentMappingDao save(BusinessSegmentMappingDao business);

    BusinessSegmentMappingDao update(BusinessSegmentMappingDao business);

    void delete(Integer id);

    BusinessSegmentMappingDao findByUUID(String uuid);

    BusinessSegmentMappingDao findById(Integer id);

    List<BusinessSegmentMappingDao> findAllByIds(List<Integer> ids);

    List<BusinessSegmentMappingDao> findAllActive();

    List<BusinessSegmentMappingDao> findAllBySegmentId(Integer businessSegmentId);
    List<BusinessSegmentMappingDao> findAllPartnerSegmentBySegmentId(Integer businessSegmentId);
    List<BusinessSegmentMappingDao> findAllVehicleSegmentBySegmentId(Integer businessSegmentId);
}