package com.goev.central.repository.vehicle.detail;

import com.goev.central.dao.vehicle.detail.VehicleModelDao;

import java.util.List;

public interface VehicleModelRepository {
    VehicleModelDao save(VehicleModelDao model);
    VehicleModelDao update(VehicleModelDao model);
    void delete(Integer id);
    VehicleModelDao findByUUID(String uuid);
    VehicleModelDao findById(Integer id);
    List<VehicleModelDao> findAllByIds(List<Integer> ids);
    List<VehicleModelDao> findAll();
}