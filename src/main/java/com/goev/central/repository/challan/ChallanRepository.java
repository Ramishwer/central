package com.goev.central.repository.challan;

import com.goev.central.dao.challan.ChallanDao;

import java.util.List;

public interface ChallanRepository {
    ChallanDao save(ChallanDao challan);
    ChallanDao update(ChallanDao challan);
    void delete(Integer id);
    ChallanDao findByUUID(String uuid);
    ChallanDao findById(Integer id);
    List<ChallanDao> findAllByIds(List<Integer> ids);
    List<ChallanDao> findAll();
}
