package com.goev.central.repository.allocation;

import com.goev.central.dao.allocation.AllocationResultDao;

import java.util.List;

public interface AllocationResultRepository {
    AllocationResultDao save(AllocationResultDao result);
    AllocationResultDao update(AllocationResultDao result);
    void delete(Integer id);
    AllocationResultDao findByUUID(String uuid);
    AllocationResultDao findById(Integer id);
    List<AllocationResultDao> findAllByIds(List<Integer> ids);
    List<AllocationResultDao> findAll();
}
