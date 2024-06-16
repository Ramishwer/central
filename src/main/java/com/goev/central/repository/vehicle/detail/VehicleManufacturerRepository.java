package com.goev.central.repository.vehicle.detail;

import com.goev.central.dao.vehicle.detail.VehicleManufacturerDao;

import java.util.List;

public interface VehicleManufacturerRepository {
    VehicleManufacturerDao save(VehicleManufacturerDao vehicleManufacturer);

    VehicleManufacturerDao update(VehicleManufacturerDao vehicleManufacturer);

    void delete(Integer id);

    VehicleManufacturerDao findByUUID(String uuid);

    VehicleManufacturerDao findById(Integer id);

    List<VehicleManufacturerDao> findAllByIds(List<Integer> ids);

    List<VehicleManufacturerDao> findAllActive();
}


