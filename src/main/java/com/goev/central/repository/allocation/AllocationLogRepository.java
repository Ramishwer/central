package com.goev.central.repository.allocation;

import com.goev.central.dao.allocation.AllocationLogDao;

import java.util.List;

public interface AllocationLogRepository {
    AllocationLogDao save(AllocationLogDao log);
    AllocationLogDao update(AllocationLogDao log);
    void delete(Integer id);
    AllocationLogDao findByUUID(String uuid);
    AllocationLogDao findById(Integer id);
    List<AllocationLogDao> findAllByIds(List<Integer> ids);
    List<AllocationLogDao> findAll();
}