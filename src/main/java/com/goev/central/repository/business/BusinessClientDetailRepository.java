package com.goev.central.repository.business;

import com.goev.central.dao.business.BusinessClientDao;
import com.goev.central.dao.business.BusinessClientDetailDao;

import java.util.List;

public interface BusinessClientDetailRepository {
    BusinessClientDetailDao save(BusinessClientDetailDao client);

    BusinessClientDetailDao update(BusinessClientDetailDao client);

    void delete(Integer id);

    BusinessClientDetailDao findByUUID(String uuid);

    BusinessClientDetailDao findById(Integer id);

    List<BusinessClientDetailDao> findAllByIds(List<Integer> ids);

    List<BusinessClientDetailDao> findAllActive();
}