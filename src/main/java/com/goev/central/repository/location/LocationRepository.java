package com.goev.central.repository.location;

import com.goev.central.dao.location.LocationDao;

import java.util.List;

public interface LocationRepository {
    LocationDao save(LocationDao partner);

    LocationDao update(LocationDao partner);

    void delete(Integer id);

    LocationDao findByUUID(String uuid);

    LocationDao findById(Integer id);

    List<LocationDao> findAllByIds(List<Integer> ids);

    List<LocationDao> findAllActive();
}