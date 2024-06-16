package com.goev.central.repository.vehicle.detail;

import com.goev.central.dao.vehicle.detail.VehicleDao;

import java.util.List;

public interface VehicleRepository {
    VehicleDao save(VehicleDao vehicle);

    VehicleDao update(VehicleDao vehicle);

    void delete(Integer id);

    VehicleDao findByUUID(String uuid);

    VehicleDao findById(Integer id);

    List<VehicleDao> findAllByIds(List<Integer> ids);

    List<VehicleDao> findAllActive();

    VehicleDao findByPlateNumber(String plateNumber);
}