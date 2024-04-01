package com.goev.central.repository.vehicle.detail;

import com.goev.central.dao.vehicle.detail.VehicleAttributeDao;

import java.util.List;

public interface VehicleAttributeRepository {
    VehicleAttributeDao save(VehicleAttributeDao attribute);
    VehicleAttributeDao update(VehicleAttributeDao attribute);
    void delete(Integer id);
    VehicleAttributeDao findByUUID(String uuid);
    VehicleAttributeDao findById(Integer id);
    List<VehicleAttributeDao> findAllByIds(List<Integer> ids);
    List<VehicleAttributeDao> findAll();
}