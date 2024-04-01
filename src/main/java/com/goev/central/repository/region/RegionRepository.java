package com.goev.central.repository.region;

import com.goev.central.dao.region.RegionDao;

import java.util.List;

public interface RegionRepository {
    RegionDao save(RegionDao partner);
    RegionDao update(RegionDao partner);
    void delete(Integer id);
    RegionDao findByUUID(String uuid);
    RegionDao findById(Integer id);
    List<RegionDao> findAllByIds(List<Integer> ids);
    List<RegionDao> findAll();
}
