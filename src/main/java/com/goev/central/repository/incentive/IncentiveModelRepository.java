package com.goev.central.repository.incentive;

import com.goev.central.dao.incentive.IncentiveModelDao;

import java.util.List;

public interface IncentiveModelRepository {
    IncentiveModelDao save(IncentiveModelDao partner);

    IncentiveModelDao update(IncentiveModelDao partner);

    void delete(Integer id);

    IncentiveModelDao findByUUID(String uuid);

    IncentiveModelDao findById(Integer id);

    List<IncentiveModelDao> findAllByIds(List<Integer> ids);

    List<IncentiveModelDao> findAllActive();
}