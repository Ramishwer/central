package com.goev.central.repository.vehicle.detail;

import com.goev.central.dao.vehicle.detail.VehicleFinancierDao;

import java.util.List;

public interface VehicleFinancierRepository {
    VehicleFinancierDao save(VehicleFinancierDao financier);
    VehicleFinancierDao update(VehicleFinancierDao financier);
    void delete(Integer id);
    VehicleFinancierDao findByUUID(String uuid);
    VehicleFinancierDao findById(Integer id);
    List<VehicleFinancierDao> findAllByIds(List<Integer> ids);
    List<VehicleFinancierDao> findAll();
}

