package com.goev.central.repository.customer.segment;

import com.goev.central.dao.customer.segment.CustomerSegmentMappingDao;

import java.util.List;

public interface CustomerSegmentMappingRepository {
    CustomerSegmentMappingDao save(CustomerSegmentMappingDao partner);
    CustomerSegmentMappingDao update(CustomerSegmentMappingDao partner);
    void delete(Integer id);
    CustomerSegmentMappingDao findByUUID(String uuid);
    CustomerSegmentMappingDao findById(Integer id);
    List<CustomerSegmentMappingDao> findAllByIds(List<Integer> ids);
    List<CustomerSegmentMappingDao> findAll();
}