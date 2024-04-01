
package com.goev.central.repository.customer.segment;

import com.goev.central.dao.customer.segment.CustomerSegmentDao;

import java.util.List;

public interface CustomerSegmentRepository {
    CustomerSegmentDao save(CustomerSegmentDao partner);
    CustomerSegmentDao update(CustomerSegmentDao partner);
    void delete(Integer id);
    CustomerSegmentDao findByUUID(String uuid);
    CustomerSegmentDao findById(Integer id);
    List<CustomerSegmentDao> findAllByIds(List<Integer> ids);
    List<CustomerSegmentDao> findAll();
}
