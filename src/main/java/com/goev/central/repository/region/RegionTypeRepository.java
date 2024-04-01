package com.goev.central.repository.region;

import com.goev.central.dao.region.RegionTypeDao;

import java.util.List;

public interface RegionTypeRepository {
    RegionTypeDao save(RegionTypeDao partner);
    RegionTypeDao update(RegionTypeDao partner);
    void delete(Integer id);
    RegionTypeDao findByUUID(String uuid);
    RegionTypeDao findById(Integer id);
    List<RegionTypeDao> findAllByIds(List<Integer> ids);
    List<RegionTypeDao> findAll();
}