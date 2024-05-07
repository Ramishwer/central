package com.goev.central.repository.location;

import com.goev.central.dao.location.LocationDetailDao;

import java.util.List;

public interface LocationDetailRepository {
    LocationDetailDao save(LocationDetailDao locationDetail);
    LocationDetailDao update(LocationDetailDao locationDetail);
    void delete(Integer id);
    LocationDetailDao findByUUID(String uuid);
    LocationDetailDao findById(Integer id);
    List<LocationDetailDao> findAllByIds(List<Integer> ids);
    List<LocationDetailDao> findAll();
}